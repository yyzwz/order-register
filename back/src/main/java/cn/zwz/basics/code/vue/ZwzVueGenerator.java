package cn.zwz.basics.code.vue;

import cn.hutool.core.util.StrUtil;
import cn.zwz.basics.exception.ZwzException;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.code.bean.Field;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@Api(tags = "Vue代码生成")
@RequestMapping(value = "/zwz/generate")
public class ZwzVueGenerator {

    @RequestMapping(value = "/table/{vueName}/{rowNum}", method = RequestMethod.POST)
    @ApiOperation(value = "生成前端Vue表格代码")
    public Result<Object> createTable(@PathVariable String vueName,@PathVariable Integer rowNum,@RequestBody List<Field> fields) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("component", generate("tableIndex.btl", false, vueName, rowNum, fields));
        map.put("componentApi", generate("tableIndex.btl", true, vueName, rowNum, fields));
        map.put("add", generate("add.btl", false, vueName, rowNum, fields));
        map.put("addApi", generate("add.btl", true, vueName, rowNum, fields));
        map.put("edit", generate("edit.btl", false, vueName, rowNum, fields));
        map.put("editApi", generate("edit.btl", true, vueName, rowNum, fields));
        map.put("single", generate("table.btl", false, vueName, rowNum, fields));
        map.put("singleApi", generate("table.btl", true, vueName, rowNum, fields));
        map.put("api", generate("api.btl", true, vueName, rowNum, fields));
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/tree/{vueName}/{rowNum}", method = RequestMethod.POST)
    @ApiOperation(value = "生成前端Vue树形代码")
    public Result<Object> createTree(@PathVariable String vueName,@PathVariable Integer rowNum,@RequestBody List<Field> fields) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("result", generate("tree.btl", false, vueName, rowNum, fields));
        map.put("resultApi", generate("tree.btl", true, vueName, rowNum, fields));
        map.put("api", generate("treeApi.btl", true, vueName, rowNum, fields));
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/getEntityData/{path}", method = RequestMethod.GET)
    @ApiOperation(value = "生成实体类的前端代码")
    public Result<Object> getEntityData(@PathVariable String path) {
        String result = null;
        try {
            result = generateEntityData(path);
        } catch (Exception e) {
            return ResultUtil.error("实体类已被删除");
        }
        return ResultUtil.data(result);
    }

    public String generateEntityData(String path) throws Exception {
        Class c = Class.forName(path);
        Object obj = c.getDeclaredConstructor().newInstance();
        String start = "{\n" +"    \"data\": [";
        String end = "\n    ]\n" + "}";
        String fieldAll = "";
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            java.lang.reflect.Field field = fields[i];
            field.setAccessible(true);
            // 字段名
            String fieldName = field.getName();
            String fieldType = field.getType().getName();
            // 白名单
            if("serialVersionUID".equals(fieldName)||"actBusinessId".equals(fieldName)||"applyUser".equals(fieldName)||"routeName".equals(fieldName)||"procInstId".equals(fieldName)||"applyTime".equals(fieldName)){
                continue;
            }
            // 注解
            ApiModelProperty myFieldAnnotation = field.getAnnotation(ApiModelProperty.class);
            String fieldNameCN = fieldName;
            if (myFieldAnnotation != null) {
                fieldNameCN = myFieldAnnotation.value();
            }
            fieldNameCN = (fieldNameCN == null || fieldNameCN == "") ? fieldName : fieldNameCN;
            String type = "text";
            String searchType = "text";
            if (fieldType.contains("Date")) {
                type = "date";
                searchType = "daterange";
            } else if (fieldType.contains("BigDecimal")) {
                type = "number";
            }
            String fieldJson = "\n        {\n" +
                    "            \"sortOrder\": " + i + ",\n" +
                    "            \"field\": \"" + fieldName + "\",\n" +
                    "            \"name\": \"" + fieldNameCN + "\",\n" +
                    "            \"level\": \"2\",\n" +
                    "            \"tableShow\": true,\n" +
                    "            \"editable\": true,\n" +
                    "            \"type\": \"" + type + "\",\n" +
                    "            \"searchType\": \"" + searchType + "\",\n" +
                    "            \"searchLevel\": \"2\",\n" +
                    "            \"validate\": false,\n" +
                    "            \"searchable\": true,\n" +
                    "            \"sortable\": false,\n" +
                    "            \"defaultSort\": false,\n" +
                    "            \"defaultSortType\": \"desc\"\n" +
                    "        }";
            String splitChar = fieldAll == "" ? "" : ",";
            fieldAll = fieldAll + splitChar + fieldJson;
        }
        return start + fieldAll + end;
    }

    public String generate(String template, boolean api, String vueName, Integer rowNum, List<Field> fields) throws IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/template/vue/");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template tableTemplate = gt.getTemplate(template);
        Collections.sort(fields, Comparator.comparing(Field::getSortOrder));
        tableTemplate.binding("api", api);
        tableTemplate.binding("vueName", name(vueName, false));
        tableTemplate.binding("apiName", name(vueName, true));
        tableTemplate.binding("fields", fields);
        Boolean upload = false, uploadThumb = false, wangEditor = false, quill = false, password = false;
        for(Field f:fields){
            if(Objects.equals("wangEditor",f.getType())){
                wangEditor = true;
            }
            if(Objects.equals("uploadThumb",f.getType())){
                uploadThumb = true;
            }
            if(Objects.equals("upload",f.getType())){
                upload = true;
            }
            if(Objects.equals("password",f.getType())){
                password = true;
            }
            if(Objects.equals("quill",f.getType())){
                quill = true;
            }
        }
        tableTemplate.binding("wangEditor", wangEditor);
        tableTemplate.binding("uploadThumb", uploadThumb);
        tableTemplate.binding("upload", upload);
        tableTemplate.binding("password", password);
        tableTemplate.binding("quill", quill);
        if(Objects.equals("table.btl",template) || Objects.equals("tableIndex.btl",template)){
            Boolean daterangeSearch = false;
            for(Field f:fields){
                if(f.getSearchable() && Objects.equals("daterange",f.getSearchType())){
                    daterangeSearch = true;
                }
            }
            tableTemplate.binding("daterangeSearch", daterangeSearch);
            // 是否隐藏搜索
            Boolean hideSearchFlag = false;
            List<Field> firstTwo = new ArrayList<>();
            List<Field> rest = new ArrayList<>();
            Integer count = 0;
            for(Field field : fields){
                if(field.getSearchable()){
                    count++;
                    if(count < 3){
                        firstTwo.add(field);
                    }else{
                        rest.add(field);
                    }
                }
            }
            if(count > 5){
                hideSearchFlag = true;
                tableTemplate.binding("firstTwo", firstTwo);
                tableTemplate.binding("rest", rest);
            }
            tableTemplate.binding("searchSize", count);
            tableTemplate.binding("hideSearch", hideSearchFlag);
            // 默认排序
            String defaultSort = "";
            String defaultSortType = "";
            for(Field field : fields){
                if(field.getDefaultSort()){
                    defaultSort = field.getField();
                    defaultSortType = field.getDefaultSortType();
                    break;
                }
            }
            tableTemplate.binding("defaultSort", defaultSort);
            tableTemplate.binding("defaultSortType", defaultSortType);
        }
        tableTemplate.binding("wangEditorWidth", "100%");
        tableTemplate.binding("quillWidth", "100%");
        tableTemplate.binding("toQuill", false);
        tableTemplate.binding("rowNum", rowNum);
        if(rowNum == 1){
            // 一行一列
            tableTemplate.binding("modalWidth", 500);
            tableTemplate.binding("width", "100%");
            tableTemplate.binding("editWidth", "100%");
            tableTemplate.binding("itemWidth", "");
            tableTemplate.binding("span", "9");
            if(Objects.equals(template,"table.btl") || Objects.equals(template,"tree.btl")) {
                tableTemplate.binding("toQuill", true);
            }
            if(Objects.equals(template,"add.btl") || Objects.equals(template,"edit.btl")) {
                tableTemplate.binding("width", "570px");
                tableTemplate.binding("rowNum", 1);
            }
        } else if(rowNum == 2){
            // 一行二列
            tableTemplate.binding("modalWidth", 770);
            tableTemplate.binding("width", "250px");
            tableTemplate.binding("editWidth", "250px");
            tableTemplate.binding("itemWidth", "350px");
            tableTemplate.binding("span", "17");
            if(Objects.equals(template,"table.btl") || Objects.equals(template,"tree.btl")) {
                tableTemplate.binding("toQuill", true);
                tableTemplate.binding("quillWidth", "610px");
            }
            if(Objects.equals(template,"add.btl") || Objects.equals(template,"edit.btl")) {
                tableTemplate.binding("width", "570px");
                tableTemplate.binding("rowNum", 1);
            }
        } else if(rowNum == 3){
            // 一行三列
            tableTemplate.binding("modalWidth", 980);
            tableTemplate.binding("width", "200px");
            tableTemplate.binding("editWidth", "200px");
            tableTemplate.binding("itemWidth", "300px");
            tableTemplate.binding("span", "17");
            if(Objects.equals(template,"table.btl") || Objects.equals(template,"tree.btl")) {
                tableTemplate.binding("quillWidth", "820px");
                tableTemplate.binding("wangEditorWidth", "820px");
            }
            if(Objects.equals(template,"add.btl") || Objects.equals(template,"edit.btl")) {
                tableTemplate.binding("width", "570px");
                tableTemplate.binding("rowNum", 1);
            }
        } else if(rowNum == 4) {
            // 一行四列
            tableTemplate.binding("modalWidth", 1130);
            tableTemplate.binding("width", "160px");
            tableTemplate.binding("editWidth", "160px");
            tableTemplate.binding("itemWidth", "260px");
            tableTemplate.binding("span", "17");
            if(Objects.equals(template,"table.btl") || Objects.equals(template,"tree.btl")) {
                tableTemplate.binding("quillWidth", "970px");
                tableTemplate.binding("wangEditorWidth", "970px");
            }
            if(Objects.equals(template,"add.btl") || Objects.equals(template,"edit.btl")) {
                tableTemplate.binding("width", "570px");
                tableTemplate.binding("rowNum", 1);
            }
        } else{
            // 不满足的情况
            throw new ZwzException("一行的输入栏只能为1到4列");
        }
        return tableTemplate.render();
    }

    /**
     * 首字母是否大小写
     * @param name
     * @param isFirstUpper
     * @return
     */
    public static String name(String name, boolean isFirstUpper){

        if(StrUtil.isBlank(name)){
            throw new ZwzException("name不能为空");
        }

        if(name.length()==1){
            if(isFirstUpper){
                return name.toUpperCase();
            } else {
                return name.toLowerCase();
            }
        }

        StringBuffer sb = new StringBuffer();
        if(isFirstUpper){
            sb.append(Character.toUpperCase(name.charAt(0)));
        } else {
            sb.append(Character.toLowerCase(name.charAt(0)));
        }
        sb.append(name.substring(1));
        return sb.toString();
    }
}
