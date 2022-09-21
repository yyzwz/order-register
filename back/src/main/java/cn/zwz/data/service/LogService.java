package cn.zwz.data.service;


import cn.zwz.basics.baseClass.ZwzBaseService;
import cn.zwz.basics.baseVo.SearchVo;
import cn.zwz.data.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 日志接口
 * @author 郑为中
 */
public interface LogService extends ZwzBaseService<Log,String> {

}
