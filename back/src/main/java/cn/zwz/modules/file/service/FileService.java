package cn.zwz.modules.file.service;

import cn.zwz.base.ZwzBaseService;
import cn.zwz.common.vo.SearchVo;
import cn.zwz.modules.file.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 文件管理接口
 * @author 郑为中
 */
public interface FileService extends ZwzBaseService<File,String> {

    /**
     * 多条件获取列表
     * @param file
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<File> findByCondition(File file, SearchVo searchVo, Pageable pageable);
}