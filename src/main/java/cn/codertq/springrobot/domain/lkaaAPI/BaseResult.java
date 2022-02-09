package cn.codertq.springrobot.domain.lkaaAPI;

import lombok.Data;

/**
 * @author coder_tq
 * @Date 2022/1/24 11:25
 */
@Data
public class BaseResult<T> {
    Integer code;
    String text;
    T data;
}
