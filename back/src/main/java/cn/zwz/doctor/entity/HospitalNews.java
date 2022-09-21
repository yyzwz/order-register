package cn.zwz.doctor.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
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
@Table(name = "a_hospital_news")
@TableName("a_hospital_news")
@ApiModel(value = "医院新闻")
public class HospitalNews extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新闻标题")
    private String newTitle;

    @ApiModelProperty(value = "新闻描述")
    private String newDescribe;

    @ApiModelProperty(value = "新闻时效")
    private String periodTime;

    @ApiModelProperty(value = "是否公开")
    private String isPublic;

    @ApiModelProperty(value = "图片")
    private String photo;

    @ApiModelProperty(value = "是否置顶：是，不是")
    private String isTop;

    @ApiModelProperty(value = "排序")
    private Integer nSort;

    @ApiModelProperty(value = "关联服务ID")
    private String relateServiceId;

    @ApiModelProperty(value = "关联服务名")
    private String relateServiceName;
}