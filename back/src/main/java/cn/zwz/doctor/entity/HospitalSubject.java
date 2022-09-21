package cn.zwz.doctor.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 郑为中
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_hospital_subject")
@TableName("a_hospital_subject")
@ApiModel(value = "科室")
public class HospitalSubject extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科室名称")
    private String subName;

    @ApiModelProperty(value = "科室代码")
    private String subCode;

    @ApiModelProperty(value = "科室人数")
    private String subNumber;

    @ApiModelProperty(value = "科室主任医师人数")
    private String superNumber;

    @Length(max = 1024)
    @ApiModelProperty(value = "科室介绍")
    private String about;

    @ApiModelProperty(value = "成立日期")
    private String startDate;

    @ApiModelProperty(value = "责任医生")
    private String dutyDoctor;

    @ApiModelProperty(value = "备注")
    private String remark;
}