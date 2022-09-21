package cn.zwz.basics.utils;

import cn.zwz.basics.exception.ZwzException;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.data.utils.ZwzNullUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 封装的分页插件
 * @author 郑为中
 */
public class PageUtil {

    private final static String[] NO_CAN_USE_WORDS = {"drop","select","master","insert","truncate","declare","delete","sleep","update","alter"};

    private static final String SORT_BY_ASC = "asc";

    private static final String SORT_BY_DESC = "desc";

    private static final String CAMEL_STEP_STR = "_";

    private static final String NULL_STR = "";

    @ApiModelProperty(value = "JPA分页方法")
    public static Pageable initPage(PageVo page){
        Pageable able = null;
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        String sort = page.getSort();
        String order = page.getOrder();
        pageNumber = pageNumber < 1 ? 1 : pageNumber;
        pageSize = pageSize < 1 ? 1 : pageSize;
        pageSize = pageSize > 100 ? 100 : pageSize;
        if(!ZwzNullUtils.isNull(sort)) {
            Sort.Direction direction = ZwzNullUtils.isNull(order) ? Sort.Direction.DESC : Sort.Direction.valueOf(order.toUpperCase());
            Sort sortBy = Sort.by(direction, sort);
            able = PageRequest.of(pageNumber - 1, pageSize, sortBy);
        } else {
            able = PageRequest.of(pageNumber - 1, pageSize);
        }
        return able;
    }

    @ApiModelProperty(value = "MybatisPlus分页方法")
    public static Page initMpPage(PageVo page){
        Page newPage = null;
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        String sort = page.getSort();
        String order = page.getOrder();
        SQLInject(sort);
        pageNumber = pageNumber < 1 ? 1 : pageNumber;
        pageSize = pageSize < 1 ? 1 : pageSize;
        pageSize = pageSize > 100 ? 100 : pageSize;
        if(!ZwzNullUtils.isNull(sort)) {
            Boolean isAsc = false;
            if(ZwzNullUtils.isNull(order)) {
                isAsc = false;
            } else {
                if(Objects.equals(order.toLowerCase(),SORT_BY_DESC)){
                    isAsc = false;
                } else if(Objects.equals(order.toLowerCase(),SORT_BY_ASC)){
                    isAsc = true;
                }
            }
            newPage = new Page(pageNumber, pageSize);
            newPage.addOrder(isAsc ? OrderItem.asc(camel2Underline(sort)) : OrderItem.desc(camel2Underline(sort)));
        } else {
            newPage = new Page(pageNumber, pageSize);
        }
        return newPage;
    }

    @ApiModelProperty(value = "自定义分页方法")
    public static List listToPage(PageVo page, List list) {
        int pageNumber = page.getPageNumber() - 1;
        int pageSize = page.getPageSize();
        pageNumber = pageNumber < 1 ? 1 : pageNumber;
        pageSize = pageSize < 1 ? 1 : pageSize;
        pageSize = pageSize > 100 ? 100 : pageSize;
        int startIndex = pageNumber * pageSize;
        int endIndex = pageNumber * pageSize + pageSize;
        if(startIndex > list.size()){
            return new ArrayList();
        } else if(endIndex > list.size() - 1) {
            return list.subList(startIndex, list.size());
        } else {
            return list.subList(startIndex, endIndex);
        }
    }

    @ApiModelProperty(value = "防 Mybatis Plus 的 SQL 注入攻击")
    public static void SQLInject(String sqlStr){
        if (ZwzNullUtils.isNull(sqlStr)) {
            return;
        }
        sqlStr = sqlStr.toLowerCase();
        for(int i = 0; i < NO_CAN_USE_WORDS.length; i ++) {
            if (sqlStr.contains(NO_CAN_USE_WORDS[i])) {
                throw new ZwzException(sqlStr + " 单词不合法");
            }
        }
    }

    @ApiModelProperty(value = "驼峰转下划线")
    public static String camel2Underline(String underlineContent) {
        if (ZwzNullUtils.isNull(underlineContent)) {
            return NULL_STR;
        }
        if(underlineContent.length() < 2){
            return underlineContent.toLowerCase();
        }
        StringBuffer camelUnderlineTempBuf = new StringBuffer();
        for(int i = 1; i < underlineContent.length(); i ++){
            camelUnderlineTempBuf.append(Character.isUpperCase(underlineContent.charAt(i)) ? CAMEL_STEP_STR + Character.toLowerCase(underlineContent.charAt(i)) : underlineContent.charAt(i));
        }
        String ans = underlineContent.charAt(0) + camelUnderlineTempBuf.toString();
        return ans.toLowerCase();
    }
}
