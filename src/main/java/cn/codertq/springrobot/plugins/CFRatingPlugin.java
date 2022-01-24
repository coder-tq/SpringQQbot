package cn.codertq.springrobot.plugins;

import cn.codertq.springrobot.domain.entity.CfUser;
import cn.codertq.springrobot.mapper.CfUserMapper;
import com.google.gson.Gson;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author coder_tq
 * @Date 2022/1/23 16:37
 */
@Component
public class CFRatingPlugin extends BotPlugin {
    @Autowired
    CfUserMapper cfUserMapper;

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
        if (!event.getMessage().contains("cf")){
            return MESSAGE_IGNORE;
        }
        final CfUser cfUser = cfUserMapper.selectByQQNumber(event.getUserId());
        bot.sendPrivateMsg(event.getUserId(),new Gson().toJson(cfUser),false);
        return super.onPrivateMessage(bot, event);
    }
}
