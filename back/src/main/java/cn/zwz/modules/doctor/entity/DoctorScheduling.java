package cn.zwz.modules.doctor.entity;

import cn.zwz.base.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 郑为中
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_doctor_scheduling")
@TableName("t_doctor_scheduling")
@ApiModel(value = "医生排班")
public class DoctorScheduling extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医生ID")
    private String doctorId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "就诊日期")
    private String date;

    @ApiModelProperty(value = "就诊编号")
    private String number;

    /**
     * 上午 | 下午
     */
    @ApiModelProperty(value = "时段")
    private String step;

    @ApiModelProperty(value = "是否预约")
    private int orderFlag;
}