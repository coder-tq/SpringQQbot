package cn.codertq.springrobot.plugins;

import cn.codertq.springrobot.config.RobotConfig;
import cn.codertq.springrobot.config.TokenConfig;
import cn.codertq.springrobot.domain.lkaaAPI.BaseResult;
import cn.codertq.springrobot.domain.lkaaAPI.DaAnDTO;
import cn.codertq.springrobot.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotContainer;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.simplerobot.modules.utils.KQCodeUtils;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author coder_tq
 * @Date 2022/2/9 10:36
 */
@Component
public class DailyNewsPlugin extends BotPlugin {
    static final String PLUGIN_KEY = "每日新闻";
    static final String URL = "https://v2.alapi.cn/api/zaobao?format=image&token=%s&rand=%s";

    @Autowired
    private BotContainer botContainer;

    @Autowired
    private RobotConfig robotConfig;

    @Autowired
    private TokenConfig tokenConfig;

    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        if (!KQCodeUtils.getInstance().remove(event.getMessage(),true).equals(PLUGIN_KEY)){
            return MESSAGE_IGNORE;
        }
        bot.sendGroupMsg(event.getGroupId(), MsgUtils.builder().img(String.format(URL,tokenConfig.getDailyNewsToken(),System.currentTimeMillis())).build(), false);
        return MESSAGE_IGNORE;
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendMsg() {
        Long botId = 2260556874L;
        Bot bot = botContainer.robots.get(botId);
        for (Long groupId : robotConfig.getDailyNewsPushGroupList()){
            bot.sendGroupMsg(groupId, "别睡了，卷起来~ 今天的每日新闻来了~", false);
            bot.sendGroupMsg(groupId, MsgUtils.builder().img(String.format(URL,tokenConfig.getDailyNewsToken(),System.currentTimeMillis())).build(), false);
        }
    }
}
