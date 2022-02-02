package cn.codertq.springrobot.plugins;

import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author coder_tq
 * @Date 2022/1/29 17:07
 */
@Component
public class TempPlugin extends BotPlugin {
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        if (StringUtils.contains(event.getMessage(),"我的小虎糕今天")){
            String pattern = "我的小虎糕今天([\\d]+)";

            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(event.getMessage());
            if (m.find()){
                if (Integer.parseInt(m.group(1)) >= 800){
                    bot.sendGroupMsg(657613385L,event.getMessage(),false);
                };
            }
        }
        return MESSAGE_IGNORE;
    }
}
