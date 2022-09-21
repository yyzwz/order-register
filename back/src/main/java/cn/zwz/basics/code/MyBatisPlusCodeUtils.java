package cn.zwz.basics.code;

import cn.hutool.core.date.DateUtil;
import cn.zwz.basics.code.bean.Entity;
import cn.zwz.basics.code.bean.Item;
import cn.hutool.core.util.StrUtil;
import cn.zwz.basics.exception.ZwzException;
import cn.zwz.data.utils.ZwzNullUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MyBatisPlusCodeUtils {

    @ApiOperation(value = "一键产生增删改查代码")
    public static void main(String[] args) throws Exception {
        ClasspathResourceLoader crl = new ClasspathResourceLoader("/template/");
        GroupTemplate gt = new GroupTemplate(crl, Configuration.defaultConfiguration());
        // 一键产生增删改查代码
        createNewCode(gt);
        // 一键移除生成的代码【需要时取消注释】
        // removeNewCode(className);
    }

    @ApiModelProperty(value = "类名")
    private static final String className = "DynamicTableData";

    @ApiModelProperty(value = "类备注")
    private static final String description = "动态表格值";

    @ApiModelProperty(value = "作者")
    private static final String author = "郑为中";

    @ApiModelProperty(value = "数据库表前缀")
    private static final String tablePre = "a_";

    @ApiModelProperty(value = "主键类型")
    private static final String primaryKeyType = "String";

    @ApiModelProperty(value = "实体类对应包")
    private static final String entityPackage = "cn.zwz.test.entity";

    @ApiModelProperty(value = "dao对应包")
    private static final String daoPackage = "cn.zwz.test.mapper";

    @ApiModelProperty(value = "service对应包")
    private static final String servicePackage = "cn.zwz.test.service";

    @ApiModelProperty(value = "serviceImpl对应包")
    private static final String serviceImplPackage = "cn.zwz.test.serviceimpl";

    @ApiModelProperty(value = "controller对应包")
    private static final String controllerPackage = "cn.zwz.test.controller";

    @ApiOperation(value = "生成代码")
    private static void createNewCode(GroupTemplate gt) throws IOException{
        Template entityTemplate = gt.getTemplate("entity.btl");
        Template daoTemplate = gt.getTemplate("mapper.btl");
        Template serviceTemplate = gt.getTemplate("mpService.btl");
        Template serviceImplTemplate = gt.getTemplate("mpServiceImpl.btl");
        Template controllerTemplate = gt.getTemplate("mpController.btl");
        Template mapperXmlTemplate = gt.getTemplate("mapperXml.btl");
        Entity entity = new Entity();
        entity.setEntityPackage(entityPackage);
        entity.setDaoPackage(daoPackage);
        entity.setServicePackage(servicePackage);
        entity.setServiceImplPackage(serviceImplPackage);
        entity.setControllerPackage(controllerPackage);
        entity.setAuthor(author);
        entity.setClassName(name(className, true));
        entity.setTableName(tablePre+camel2Underline(className));
        entity.setClassNameLowerCase(name(className, false));
        entity.setDescription(description);
        entity.setPrimaryKeyType(primaryKeyType);
        entity.setActiviti(false);
        entity.setIsTree(false);
        OutputStream out = null;
        /**
         * 生成实体类代码
         */
        entityTemplate.binding("entity", entity);
        String entityRrender = entityTemplate.render();
        String entityFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(entityPackage) + "/" + className + ".java";
        File entityFile = new File(entityFileUrl);
        File entityDir = entityFile.getParentFile();
        if (!entityDir.exists()) {
            entityDir.mkdirs();
        }
        if(!entityFile.exists()){
            // 若文件存在则不重新生成
            entityFile.createNewFile();
            out = new FileOutputStream(entityFile);
            entityTemplate.renderTo(out);
        }
        /**
         * Dao代码
         */
        daoTemplate.binding("entity",entity);
        String daoResult = daoTemplate.render();
        String daoFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(daoPackage) + "/" +className + "Mapper.java";
        File daoFile = new File(daoFileUrl);
        File daoDir = daoFile.getParentFile();
        if (!daoDir.exists()) {
            daoDir.mkdirs();
        }
        if(!daoFile.exists()) {
            daoFile.createNewFile();
            out = new FileOutputStream(daoFile);
            daoTemplate.renderTo(out);
        }
        /**
         * Service代码
         */
        serviceTemplate.binding("entity",entity);
        String serviceResult = serviceTemplate.render();
        String serviceFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(servicePackage) + "/I" + className + "Service.java";
        File serviceFile = new File(serviceFileUrl);
        File serviceDir = serviceFile.getParentFile();
        if (!serviceDir.exists()) {
            serviceDir.mkdirs();
        }
        if(!serviceFile.exists()) {
            serviceFile.createNewFile();
            out = new FileOutputStream(serviceFile);
            serviceTemplate.renderTo(out);
        }
        /**
         * 生成serviceImpl代码
         */
        serviceImplTemplate.binding("entity",entity);
        String serviceImplResult = serviceImplTemplate.render();
        String serviceImplFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(serviceImplPackage) + "/I" + className + "ServiceImpl.java";
        File serviceImplFile = new File(serviceImplFileUrl);
        File serviceImplDir = serviceImplFile.getParentFile();
        if (!serviceImplDir.exists()) {
            serviceImplDir.mkdirs();
        }
        if(!serviceImplFile.exists()) {
            serviceImplFile.createNewFile();
            out = new FileOutputStream(serviceImplFile);
            serviceImplTemplate.renderTo(out);
        }
        /**
         * 生成controller代码
         */
        controllerTemplate.binding("entity",entity);
        String controllerResult = controllerTemplate.render();
        String controllerFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(controllerPackage) + "/" + className + "Controller.java";
        File controllerFile = new File(controllerFileUrl);
        File controllerDir = controllerFile.getParentFile();
        if (!controllerDir.exists()) {
            controllerDir.mkdirs();
        }
        if(!controllerFile.exists()) {
            controllerFile.createNewFile();
            out = new FileOutputStream(controllerFile);
            controllerTemplate.renderTo(out);
        }
        /**
         * 生成mapperXml代码
         */
        mapperXmlTemplate.binding("entity",entity);
        String mapperXmlResult = mapperXmlTemplate.render();
        String mapperXmlFileUrl = System.getProperty("user.dir")+"/src/main/resources/mapper/" + className + "Mapper.xml";
        File mapperXmlFile = new File(mapperXmlFileUrl);
        File mapperXmlDir = mapperXmlFile.getParentFile();
        if (!mapperXmlDir.exists()) {
            mapperXmlDir.mkdirs();
        }
        if(!mapperXmlFile.exists()) {
            mapperXmlFile.createNewFile();
            out = new FileOutputStream(mapperXmlFile);
            mapperXmlTemplate.renderTo(out);
        }
        // 关闭流
        if(out!=null){
            out.close();
        }
        log.info("【生成代码成功】" + DateUtil.now());
    }

    @ApiOperation(value = "删除代码")
    private static void removeNewCode(String className) {
        String entityFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(entityPackage) + "/" +className+".java";
        File entityFile = new File(entityFileUrl);
        if(entityFile.exists()){
            entityFile.delete();
        }
        String daoFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(daoPackage) + "/" +className+"Mapper.java";
        File daoFile = new File(daoFileUrl);
        if(daoFile.exists()){
            daoFile.delete();
        }

        String serviceFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(servicePackage) + "/I" +className+"Service.java";
        File serviceFile = new File(serviceFileUrl);
        if(serviceFile.exists()){
            serviceFile.delete();
        }

        String serviceImplFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(serviceImplPackage) + "/I" +className+"ServiceImpl.java";
        File serviceImplFile = new File(serviceImplFileUrl);
        if(serviceImplFile.exists()){
            serviceImplFile.delete();
        }

        String controllerFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(controllerPackage) + "/" +className+"Controller.java";
        File controllerFile = new File(controllerFileUrl);
        if(controllerFile.exists()){
            controllerFile.delete();
        }

        String mapperXmlFileUrl = System.getProperty("user.dir")+"/src/main/resources/mapper/" + className + "Mapper.xml";
        File mapperXmlFile = new File(mapperXmlFileUrl);
        if(mapperXmlFile.exists()){
            mapperXmlFile.delete();
        }
        log.info("【删除代码成功】" + DateUtil.now());
    }

    @ApiOperation(value = "点转斜线")
    public static String dotToLine(String dotContext){
        return dotContext.replace(".", "/");
    }

    @ApiOperation(value = "驼峰法转下划线")
    public static String camel2Underline(String camelContext) {
        if (ZwzNullUtils.isNull(camelContext)) {
            return "";
        }
        if(Objects.equals(1,camelContext.length())){
            return camelContext.toLowerCase();
        }
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 1; i < camelContext.length(); i ++){
            if(Character.isUpperCase(camelContext.charAt(i))){
                stringBuffer.append("_" + Character.toLowerCase(camelContext.charAt(i)));
            }else{
                stringBuffer.append(camelContext.charAt(i));
            }
        }
        return (camelContext.charAt(0) + stringBuffer.toString()).toLowerCase();
    }

    @ApiOperation(value = "首字母是否大小写")
    public static String name(String classTitle, boolean isFirstUpper){
        if(ZwzNullUtils.isNull(classTitle)){
            throw new ZwzException("类名的长度必须是正数");
        }
        if(Objects.equals(1,classTitle.length())){
            return isFirstUpper ? classTitle.toUpperCase() : classTitle.toLowerCase();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(isFirstUpper ? Character.toUpperCase(classTitle.charAt(0)) : Character.toLowerCase(classTitle.charAt(0)));
        stringBuffer.append(classTitle.substring(1));
        return stringBuffer.toString();
    }
}
