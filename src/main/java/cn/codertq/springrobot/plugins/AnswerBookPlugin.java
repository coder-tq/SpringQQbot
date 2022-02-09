package cn.codertq.springrobot.plugins;

import cn.codertq.springrobot.domain.lkaaAPI.BaseResult;
import cn.codertq.springrobot.domain.lkaaAPI.DaAnDTO;
import cn.codertq.springrobot.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.simplerobot.modules.utils.KQCodeUtils;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * http://lkaa.top/API/daan/api
 * @author coder_tq
 * @Date 2022/2/2 21:11
 */
@Component
public class AnswerBookPlugin extends BotPlugin {
    static final String PLUGIN_KEY = "答案之书";
    static final String URL = "http://lkaa.top/API/daan/api";
    static final String MESSAGE_TEMPLATE = "火火找寻答案回来啦~\n" +
            "----------------------------------\n" +
            "%s\n" +
            "%s\n" +
            "----------------------------------\n";

    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        if (!KQCodeUtils.getInstance().remove(event.getMessage(),true).equals(PLUGIN_KEY)){
            return MESSAGE_IGNORE;
        }
        bot.sendGroupMsg(event.getGroupId(),"请在脑海中想一个问题，说出来就不灵了哦，10秒后展示答案~",false);
        Thread.sleep(10000);
        final String s = HttpUtil.get(URL, null);
        final BaseResult<DaAnDTO> result = new Gson().fromJson(s, new TypeToken<BaseResult<DaAnDTO>>() {
        }.getType());
        bot.sendGroupMsg(event.getGroupId(),String.format(MESSAGE_TEMPLATE,result.getData().getZh(),result.getData().getEn()),false);
        return MESSAGE_IGNORE;
    }
}
