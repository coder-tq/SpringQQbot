package cn.codertq.springrobot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author coder_tq
 * @Date 2022/1/22 10:23
 */

@Data
@Component
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    String cxAuthorityToken;
    String dailyNewsToken;
}
