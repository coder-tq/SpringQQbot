package cn.codertq.springrobot.domain.muxiaoguoAPI;

import lombok.Data;

/**
 * @author coder_tq
 * @Date 2022/1/24 11:25
 */
@Data
public class BaseResult<T> {
    String code;
    String msg;
    T data;
}
