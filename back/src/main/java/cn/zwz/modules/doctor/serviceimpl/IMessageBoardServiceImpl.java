package cn.zwz.modules.doctor.serviceimpl;

import cn.zwz.modules.doctor.mapper.MessageBoardMapper;
import cn.zwz.modules.doctor.entity.MessageBoard;
import cn.zwz.modules.doctor.service.IMessageBoardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 留言板接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IMessageBoardServiceImpl extends ServiceImpl<MessageBoardMapper, MessageBoard> implements IMessageBoardService {

    @Autowired
    private MessageBoardMapper messageBoardMapper;
}