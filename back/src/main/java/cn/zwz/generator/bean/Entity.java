package cn.zwz.generator.bean;

import lombok.Data;

/**
 * @author 郑为中
 */
@Data
public class Entity {

    private String entityPackage;

    private String daoPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String controllerPackage;

    private String author;

    private String className;

    private String classNameLowerCase;

    private String tableName;

    private String description;

    private String primaryKeyType;

    private Boolean activiti;

    private Boolean isTree;
}
