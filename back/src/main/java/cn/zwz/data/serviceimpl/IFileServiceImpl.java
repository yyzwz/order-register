package cn.zwz.data.serviceimpl;

import cn.zwz.data.dao.mapper.FileMapper;
import cn.zwz.data.entity.File;
import cn.zwz.data.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统文件 服务层实现
 * @author 郑为中
 */
@Service
public class IFileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
