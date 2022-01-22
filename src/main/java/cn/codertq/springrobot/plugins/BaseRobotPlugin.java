package cn.codertq.springrobot.plugins;

import cn.codertq.springrobot.config.RobotConfig;
import com.mikuac.shiro.common.utils.ShiroUtils;
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
 * 机器人基础插件，用于自动同意好友请求，屏蔽黑名单私聊，群组白名单功能。
 */
@Component
public class BaseRobotPlugin extends BotPlugin {

    @Autowired
    RobotConfig robotConfig;

    /**
     * 主要用于过滤来自黑名单的私聊消息，防止恶意使用机器人，黑名单位于配置中心。
     */
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

    /**
     * 过滤来自白名单的群组，防止机器人在一些未被允许的群组发送消息而被移除或举报
     */
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        if (robotConfig.getWhiteGroupList().contains(event.getGroupId())||robotConfig.getWhiteUserList().contains(event.getUserId())){
            return MESSAGE_IGNORE;
        }
        if (ShiroUtils.getAtList(event.getArrayMsg()).contains(bot.getSelfId())){
            bot.sendGroupMsg(event.getGroupId(),"本群("+event.getGroupId()+")暂未开启机器人，请联系QQ909413805开启相应的群功能",false);
        }
        return MESSAGE_BLOCK;
    }

    /**
     * 过滤来自白名单的群组撤回通知，防止机器人在一些未被允许的群组发送消息而被移除或举报
     */
    @Override
    public int onGroupMsgDeleteNotice(@NotNull Bot bot, @NotNull GroupMsgDeleteNoticeEvent event) {
        if (robotConfig.getWhiteGroupList().contains(event.getGroupId())||robotConfig.getWhiteUserList().contains(event.getUserId())){
            return MESSAGE_IGNORE;
        }
        return MESSAGE_BLOCK;
    }

    /**
     * 自动通过好友添加请求
     */
    @Override
    public int onFriendAddRequest(@NotNull Bot bot, @NotNull FriendAddRequestEvent event) {
        bot.setFriendAddRequest(event.getFlag(), true, null);
        return MESSAGE_BLOCK;
    }

}
