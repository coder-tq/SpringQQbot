package cn.codertq.springrobot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author coder_tq
 * @Date 2022/1/21 11:50
 */
@Data
@Component
@ConfigurationProperties(prefix = "robot")
public class RobotConfig {
    List<Long> whiteGroupList;
    List<Long> whiteUserList;
    List<Long> blackUserList;
    List<Long> dailyNewsPushGroupList;
}
