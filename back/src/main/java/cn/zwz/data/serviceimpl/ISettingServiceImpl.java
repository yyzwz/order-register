package cn.zwz.data.serviceimpl;

import cn.zwz.data.dao.mapper.SettingMapper;
import cn.zwz.data.entity.Setting;
import cn.zwz.data.service.ISettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 设置 服务层实现
 * @author 郑为中
 */
@Service
public class ISettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {

}
