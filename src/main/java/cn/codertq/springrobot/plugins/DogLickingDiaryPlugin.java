package cn.codertq.springrobot.plugins;

import cn.codertq.springrobot.domain.muxiaoguoAPI.BaseResult;
import cn.codertq.springrobot.domain.muxiaoguoAPI.TianGouRiJiDTO;
import cn.codertq.springrobot.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikuac.shiro.common.utils.ShiroUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.simplerobot.modules.utils.KQCodeUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author coder_tq
 * @Date 2022/1/24 11:19
 */
@Deprecated
//@Component
public class DogLickingDiaryPlugin extends BotPlugin {

    static final String PLUGIN_KEY = "舔狗日记";
    static final String URL = "https://api.muxiaoguo.cn/api/tiangourj?api_key=e24c75b9b93e8783";


    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        if (!KQCodeUtils.getInstance().remove(event.getMessage(),true).equals(PLUGIN_KEY)){
            return MESSAGE_IGNORE;
        }

        final String s = HttpUtil.get(URL, null);

        final BaseResult<TianGouRiJiDTO> result = new Gson().fromJson(s, new TypeToken<BaseResult<TianGouRiJiDTO>>() {
        }.getType());
        bot.sendGroupMsg(event.getGroupId(),result.getData().getComment(),false);
        return MESSAGE_IGNORE;
    }
}
