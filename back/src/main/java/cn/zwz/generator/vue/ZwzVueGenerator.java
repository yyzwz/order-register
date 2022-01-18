package cn.zwz.generator.vue;

import cn.zwz.common.exception.LimitException;
import cn.zwz.common.exception.ZwzException;
import cn.zwz.common.limit.RedisRaterLimiter;
import cn.zwz.common.utils.IpInfoUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.vo.Result;
import cn.zwz.generator.ZwzGenerator;
import cn.zwz.generator.bean.Field;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author nikou
 */
@Slf4j
@RestController
@Api(tags = "Vue代码生成")
@RequestMapping(value = "/zwz/generate")
public class ZwzVueGenerator {

    @Autowired
    private RedisRaterLimiter redisRaterLimiter;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @RequestMapping(value = "/table/{vueName}/{rowNum}", method = RequestMethod.POST)
    @ApiOperation(value = "增删改表格生成")
    public Result<Object> generateTable(@PathVariable String vueName,
                                        @PathVariable Integer rowNum,
                                        @RequestBody List<Field> fields,
                                        HttpServletRequest request) throws IOException {

        String component = generate("tableIndex.btl", false, vueName, rowNum, fields);
        String componentApi = generate("tableIndex.btl", true, vueName, rowNum, fields);
        String add = generate("add.btl", false, vueName, rowNum, fields);
        String addApi = generate("add.btl", true, vueName, rowNum, fields);
        String edit = generate("edit.btl", false, vueName, rowNum, fields);
        String editApi = generate("edit.btl", true, vueName, rowNum, fields);
        String single = generate("table.btl", false, vueName, rowNum, fields);
        String singleApi = generate("table.btl", true, vueName, rowNum, fields);
        String api = generate("api.btl", true, vueName, rowNum, fields);
        Map<String, String> map = new HashMap<>();
        map.put("component", component);
        map.put("componentApi", componentApi);
        map.put("add", add);
        map.put("addApi", addApi);
        map.put("edit", edit);
        map.put("editApi", editApi);
        map.put("single", single);
        map.put("singleApi", singleApi);
        map.put("api", api);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/tree/{vueName}/{rowNum}", method = RequestMethod.POST)
    @ApiOperation(value = "树形结构生成")
    public Result<Object> generateTree(@PathVariable String vueName,
                                       @PathVariable Integer rowNum,
                                       @RequestBody List<Field> fields,
                                       HttpServletRequest request) throws IOException {
        String result = generate("tree.btl", false, vueName, rowNum, fields);
        String resultApi = generate("tree.btl", true, vueName, rowNum, fields);
        String api = generate("treeApi.btl", true, vueName, rowNum, fields);
        Map<String, String> map = new HashMap<>();
        map.put("result", result);
        map.put("resultApi", resultApi);
        map.put("api", api);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/getEntityData/{path}", method = RequestMethod.GET)
    @ApiOperation(value = "通过实体类生成Vue代码Json数据")
    public Result<Object> getEntityData(@PathVariable String path) {

        String result = "";
        try {
            result = generateEntityData(path);
        } catch (Exception e) {
            return ResultUtil.error("实体类文件不存在");
        }
        return ResultUtil.data(result);
    }

    public String generate(String template, boolean api, String vueName, Integer rowNum, List<Field> fields) throws IOException {

        // 模板路径
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/btl/vue/");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

        Template tableTemplate = gt.getTemplate(template);
        // 排序
        Collections.sort(fields, Comparator.comparing(Field::getSortOrder));
        // 绑定变量
        tableTemplate.binding("api", api);
        tableTemplate.binding("vueName", ZwzGenerator.name(vueName, false));
        tableTemplate.binding("apiName", ZwzGenerator.name(vueName, true));
        tableTemplate.binding("fields", fields);
        // 判断有无upload和日期范围搜索等组件
        Boolean upload = false, uploadThumb = false, wangEditor = false, quill = false, password = false;
        for(Field f:fields){
            if("upload".equals(f.getType())){
                upload = true;
            }
            if("uploadThumb".equals(f.getType())){
                uploadThumb = true;
            }
            if("wangEditor".equals(f.getType())){
                wangEditor = true;
            }
            if("quill".equals(f.getType())){
                quill = true;
            }
            if("password".equals(f.getType())){
                password = true;
            }
        }
        tableTemplate.binding("upload", upload);
        tableTemplate.binding("uploadThumb", uploadThumb);
        tableTemplate.binding("wangEditor", wangEditor);
        tableTemplate.binding("quill", quill);
        tableTemplate.binding("password", password);
        if("table.btl".equals(template)||"tableIndex.btl".equals(template)){
            // 判断有无upload和日期范围搜索
            Boolean daterangeSearch = false;
            for(Field f:fields){
                if(f.getSearchable()&&"daterange".equals(f.getSearchType())){
                    daterangeSearch = true;
                }
            }
            tableTemplate.binding("daterangeSearch", daterangeSearch);
            // 统计搜索栏个数 判断是否隐藏搜索栏
            Boolean hideSearch = false;
            List<Field> firstTwo = new ArrayList<>();
            List<Field> rest = new ArrayList<>();
            Integer count = 0;
            for(Field f:fields){
                if(f.getSearchable()){
                    count++;
                    if(count<=2){
                        firstTwo.add(f);
                    }else{
                        rest.add(f);
                    }
                }
            }
            if(count>=4){
                hideSearch = true;
                tableTemplate.binding("firstTwo", firstTwo);
                tableTemplate.binding("rest", rest);
            }
            tableTemplate.binding("searchSize", count);
            tableTemplate.binding("hideSearch", hideSearch);
            // 获取默认排序字段
            String defaultSort = "", defaultSortType = "";
            for(Field f:fields){
                if(f.getDefaultSort()){
                    defaultSort = f.getField();
                    defaultSortType = f.getDefaultSortType();
                    break;
                }
            }
            tableTemplate.binding("defaultSort", defaultSort);
            tableTemplate.binding("defaultSortType", defaultSortType);
        }
        tableTemplate.binding("wangEditorWidth", "100%");
        tableTemplate.binding("quillWidth", "100%");
        tableTemplate.binding("toQuill", false);
        // 一行几列
        tableTemplate.binding("rowNum", rowNum);
        if(rowNum==1){
            tableTemplate.binding("modalWidth", 500);
            tableTemplate.binding("width", "100%");
            tableTemplate.binding("editWidth", "100%");
            tableTemplate.binding("itemWidth", "");
            tableTemplate.binding("span", "9");
            if("table.btl".equals(template)||"tree.btl".equals(template)){
                tableTemplate.binding("toQuill", true);
            }
            if("add.btl".equals(template)||"edit.btl".equals(template)){
                tableTemplate.binding("width", "570px");
                tableTemplate.binding("rowNum", 1);
            }
        }else if(rowNum==2){
            tableTemplate.binding("modalWidth", 770);
            tableTemplate.binding("width", "250px");
            tableTemplate.binding("editWidth", "250px");
            tableTemplate.binding("itemWidth", "350px");
            tableTemplate.binding("span", "17");
            if("table.btl".equals(template)||"tree.btl".equals(template)){
                tableTemplate.binding("toQuill", true);
                tableTemplate.binding("quillWidth", "610px");
            }
            if("add.btl".equals(template)||"edit.btl".equals(template)){
                tableTemplate.binding("width", "570px");
                tableTemplate.binding("rowNum", 1);
            }
        }else if(rowNum==3){
            tableTemplate.binding("modalWidth", 980);
            tableTemplate.binding("width", "200px");
            tableTemplate.binding("editWidth", "200px");
            tableTemplate.binding("itemWidth", "300px");
            tableTemplate.binding("span", "17");
            if("table.btl".equals(template)||"tree.btl".equals(template)){
                tableTemplate.binding("quillWidth", "820px");
                tableTemplate.binding("wangEditorWidth", "820px");
            }
            if("add.btl".equals(template)||"edit.btl".equals(template)){
                tableTemplate.binding("width", "570px");
                tableTemplate.binding("rowNum", 1);
            }
        }else if(rowNum==4){
            tableTemplate.binding("modalWidth", 1130);
            tableTemplate.binding("width", "160px");
            tableTemplate.binding("editWidth", "160px");
            tableTemplate.binding("itemWidth", "260px");
            tableTemplate.binding("span", "17");
            if("table.btl".equals(template)||"tree.btl".equals(template)){
                tableTemplate.binding("quillWidth", "970px");
                tableTemplate.binding("wangEditorWidth", "970px");
            }
            if("add.btl".equals(template)||"edit.btl".equals(template)){
                tableTemplate.binding("width", "570px");
                tableTemplate.binding("rowNum", 1);
            }
        }else{
            throw new ZwzException("rowNum仅支持数字1-4");
        }
        // 生成代码
        String result = tableTemplate.render();
        return result;
    }

    public String generateEntityData(String path) throws Exception {

        Class c = Class.forName(path);

        Object obj = c.getDeclaredConstructor().newInstance();

        String start = "{\n" +
                "    \"data\": [";
        String end = "\n    ]\n" +
                "}";
        String fieldAll = "";
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            java.lang.reflect.Field field = fields[i];
            field.setAccessible(true);
            // 字段名
            String fieldName = field.getName();
            String fieldType = field.getType().getName();
            // 白名单
            if("serialVersionUID".equals(fieldName)||"actBusinessId".equals(fieldName)||"applyUser".equals(fieldName)
                    ||"routeName".equals(fieldName)||"procInstId".equals(fieldName)||"applyTime".equals(fieldName)){
                continue;
            }

            // 获得字段注解
            ApiModelProperty myFieldAnnotation = field.getAnnotation(ApiModelProperty.class);
            String fieldNameCN = fieldName;
            if (myFieldAnnotation != null) {
                fieldNameCN = myFieldAnnotation.value();
            }
            fieldNameCN = (fieldNameCN == null || fieldNameCN == "") ? fieldName : fieldNameCN;

            String type = "text";
            String searchType = "text";
            // 日期数字字段特殊处理 其他一律按字符串处理
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
        String json = start + fieldAll + end;
        return json;
    }
}
