package cn.codertq.springrobot.plugins;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.common.utils.ShiroUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author coder_tq
 * @Date 2022/1/22 10:47
 */
@Component
public class MenuPlugin extends BotPlugin {
    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
        return super.onPrivateMessage(bot, event);
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        if (!ShiroUtils.getAtList(event.getArrayMsg()).contains(bot.getSelfId())){
            return MESSAGE_IGNORE;
        }

        if (event.getMessage().contains("菜单")||event.getMessage().contains("功能")){
            bot.sendGroupMsg(event.getGroupId(),"这里是菜单",false);
            return MESSAGE_BLOCK;
        }


        return MESSAGE_IGNORE;
    }
}
