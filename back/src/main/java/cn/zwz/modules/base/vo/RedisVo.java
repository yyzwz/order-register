package cn.zwz.modules.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 郑为中
 */
@Data
@AllArgsConstructor
public class RedisVo {

    private String key;

    private String value;

    private Long expireTime;
}
