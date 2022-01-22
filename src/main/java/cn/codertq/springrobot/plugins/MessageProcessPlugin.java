package cn.codertq.springrobot.plugins;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.common.utils.ShiroUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
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

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
        bot.sendPrivateMsg(event.getUserId(), event.getMessage(), false);
        log.info("接收到来自{}的消息：{}",event.getUserId(),event.getMessage());
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        redisTemplate.opsForValue().set(String.valueOf(event.getMessageId()), event.getMessage());
        if (ShiroUtils.getAtList(event.getArrayMsg()).contains(bot.getSelfId())) {
            bot.sendGroupMsg(event.getGroupId(), event.getMessage()
                    ,false);
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMsgDeleteNotice(@NotNull Bot bot, @NotNull GroupMsgDeleteNoticeEvent event) {
        bot.sendGroupMsg(event.getGroupId(),redisTemplate.opsForValue().get(String.valueOf(event.getMsgId())),false);
        return MESSAGE_IGNORE;
    }
}