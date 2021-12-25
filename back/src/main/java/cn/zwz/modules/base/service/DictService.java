package cn.zwz.modules.base.service;

import cn.zwz.base.ZwzBaseService;
import cn.zwz.modules.base.entity.Dict;

import java.util.List;

/**
 * 字典接口
 * @author 郑为中
 */
public interface DictService extends ZwzBaseService<Dict,String> {

    /**
     * 排序获取全部
     * @return
     */
    List<Dict> findAllOrderBySortOrder();

    /**
     * 通过type获取
     * @param type
     * @return
     */
    Dict findByType(String type);

    /**
     * 模糊搜索
     * @param key
     * @return
     */
    List<Dict> findByTitleOrTypeLike(String key);
}