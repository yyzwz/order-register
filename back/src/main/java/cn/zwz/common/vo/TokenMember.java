package cn.zwz.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 郑为中
 */
@Data
@AllArgsConstructor
public class TokenMember implements Serializable{

    private String username;

    private Integer platform;
}
