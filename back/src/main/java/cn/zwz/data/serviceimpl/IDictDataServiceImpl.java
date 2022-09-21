package cn.zwz.data.serviceimpl;

import cn.zwz.data.dao.mapper.DictDataMapper;
import cn.zwz.data.entity.DictData;
import cn.zwz.data.service.IDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 数据字典值 服务层实现
 * @author 郑为中
 */
@Service
public class IDictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {

}
