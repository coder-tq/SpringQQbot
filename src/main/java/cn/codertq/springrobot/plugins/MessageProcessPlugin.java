package cn.codertq.springrobot.plugins;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.common.utils.ShiroUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.action.common.ActionData;
import com.mikuac.shiro.dto.action.response.GroupMemberInfoResp;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import com.mikuac.shiro.dto.event.notice.GroupMsgDeleteNoticeEvent;
import com.simplerobot.modules.utils.CQDecoder;
import com.simplerobot.modules.utils.KQCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author coder_tq
 * @Date 2022/1/19 13:01
 */
@Slf4j
@Component
public class MessageProcessPlugin extends BotPlugin {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    static String MESSAGE_DELETE_TEMPLATE = "%s\n\t--%s撤回了一条消息";

    /**
     * 简易复读机
     * @param bot
     * @param event
     * @return
     */
    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
        bot.sendPrivateMsg(event.getUserId(), event.getMessage(), false);
        log.info("接收到来自{}的消息：{}",event.getUserId(),event.getMessage());
        return MESSAGE_IGNORE;
    }

    /**
     * 检测群中@机器人的消息并进行复读，所有消息都保存在redis中，当检测到撤回消息时可以防撤回
     * @param bot
     * @param event
     * @return
     */
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        redisTemplate.opsForValue().set(String.valueOf(event.getMessageId()), event.getMessage());
        if (ShiroUtils.getAtList(event.getArrayMsg()).contains(bot.getSelfId())) {
            bot.sendGroupMsg(event.getGroupId(), event.getMessage()
                    ,false);
        }
        return MESSAGE_IGNORE;
    }

    /**
     * 防撤回
     * @param bot
     * @param event
     * @return
     */
    @Override
    public int onGroupMsgDeleteNotice(@NotNull Bot bot, @NotNull GroupMsgDeleteNoticeEvent event) {
        if(bot.getSelfId() == event.getUserId()) {
            return MESSAGE_IGNORE;
        }
        final ActionData<GroupMemberInfoResp> memberInfo = bot.getGroupMemberInfo(event.getGroupId(), event.getUserId(), false);
        bot.sendGroupMsg(event.getGroupId(),String.format(MESSAGE_DELETE_TEMPLATE,redisTemplate.opsForValue().get(String.valueOf(event.getMsgId())),memberInfo.getData().getCard()),false);
        return MESSAGE_IGNORE;
    }
}
