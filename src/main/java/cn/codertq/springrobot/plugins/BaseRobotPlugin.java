package cn.codertq.springrobot.plugins;

import cn.codertq.springrobot.config.RobotConfig;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import com.mikuac.shiro.dto.event.notice.FriendAddNoticeEvent;
import com.mikuac.shiro.dto.event.notice.GroupMsgDeleteNoticeEvent;
import com.mikuac.shiro.dto.event.request.FriendAddRequestEvent;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author coder_tq
 * @Date 2022/1/19 16:09
 */
@Component
public class BaseRobotPlugin extends BotPlugin {

    @Autowired
    RobotConfig robotConfig;

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
        if (robotConfig.getWhiteUserList().contains(event.getUserId())){
            return MESSAGE_IGNORE;
        }
        if (robotConfig.getBlackUserList().contains(event.getUserId())){
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        if (robotConfig.getWhiteGroupList().contains(event.getGroupId())||robotConfig.getWhiteUserList().contains(event.getUserId())){
            return MESSAGE_IGNORE;
        }
        return MESSAGE_BLOCK;
    }

    @Override
    public int onFriendAddRequest(@NotNull Bot bot, @NotNull FriendAddRequestEvent event) {
        bot.setFriendAddRequest(event.getFlag(), true, null);
        return MESSAGE_BLOCK;
    }

}
