package cn.codertq.springrobot.plugins;

import cn.codertq.springrobot.config.TokenConfig;
import cn.codertq.springrobot.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author coder_tq
 * @Date 2022/1/21 17:26
 */
@Component
public class AnswerQueryPlugin extends BotPlugin {

    static String URL = "https://cx.icodef.com/v2/answer";
    static String KEY = "topic[0]";
    static String MESSAGE_TEMPLATE = "题目:%s\n答案:%s\n";
    static String FAIL_TEMPLATE = "没有查询到答案呢~\n换道题目试试吧！";
    @Autowired
    TokenConfig tokenConfig;

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.put(KEY, Collections.singletonList(event.getMessage()));
        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("authority", Collections.singletonList(tokenConfig.getCxAuthorityToken()));
        final JsonElement jsonElement = JsonParser.parseString(HttpUtil.post(URL, param, headers)).getAsJsonArray().get(0);
        String topic = jsonElement.getAsJsonObject().get("topic").toString();
        if (jsonElement.getAsJsonObject().get("result").getAsJsonArray().size() == 0){
            bot.sendPrivateMsg(event.getUserId(),FAIL_TEMPLATE,false);
        }
        String answer = jsonElement.getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject().get("correct").getAsJsonArray().get(0).getAsJsonObject().get("content").toString();
        bot.sendPrivateMsg(event.getUserId(),String.format(MESSAGE_TEMPLATE,topic,answer),false);
        return MESSAGE_BLOCK;
    }

}
