package cn.zwz.data.serviceimpl;

import cn.zwz.data.dao.mapper.DepartmentHeaderMapper;
import cn.zwz.data.entity.DepartmentHeader;
import cn.zwz.data.service.IDepartmentHeaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 部门领导 服务层实现
 * @author 郑为中
 */
@Service
public class IDepartmentHeaderServiceImpl extends ServiceImpl<DepartmentHeaderMapper, DepartmentHeader> implements IDepartmentHeaderService {

}
