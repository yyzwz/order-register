package cn.zwz.data.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author 郑为中
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_log")
@TableName("a_log")
@ApiModel(value = "日志")
public class Log extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志标题")
    private String name;

    @ApiModelProperty(value = "日志类型")
    private Integer logType;

    @ApiModelProperty(value = "日志代码")
    private String code;

    @ApiModelProperty(value = "设备")
    private String device;

    @ApiModelProperty(value = "请求URL")
    private String requestUrl;

    @ApiModelProperty(value = "请求方式")
    private String requestType;

    @ApiModelProperty(value = "参数")
    private String requestParam;

    @ApiModelProperty(value = "触发者")
    private String username;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "IP定位")
    private String ipInfo;

    @ApiModelProperty(value = "消耗毫秒数")
    private Integer costTime;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "搜索开始时间")
    private String startDate;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "搜索结束时间")
    private String endDate;
}
