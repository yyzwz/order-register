package cn.zwz.test.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author 郑为中
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_teacher")
@TableName("a_teacher")
@ApiModel(value = "教师")
public class Teacher extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "年龄")
    private BigDecimal age;

    @ApiModelProperty(value = "毕业院校")
    private String graduated;

    @ApiModelProperty(value = "工资")
    private BigDecimal wages;

    @ApiModelProperty(value = "在职状态")
    private String status;

    @ApiModelProperty(value = "签名")
    private String autograph;

    @ApiModelProperty(value = "个人简历")
    private String resume;

    @ApiModelProperty(value = "备注")
    private String remark;
}