package cn.zwz.modules.doctor.controller;

import cn.zwz.common.utils.PageUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.utils.SecurityUtil;
import cn.zwz.common.vo.PageVo;
import cn.zwz.common.vo.Result;
import cn.zwz.modules.base.entity.User;
import cn.zwz.modules.base.utils.ZwzNullUtils;
import cn.zwz.modules.doctor.entity.MessageBoard;
import cn.zwz.modules.doctor.service.IMessageBoardService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 郑为中
 */
@Slf4j
@RestController
@Api(tags = "留言板管理")
@RequestMapping("/zwz/messageBoard")
@Transactional
public class MessageBoardController {

    @Autowired
    private IMessageBoardService iMessageBoardService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个留言")
    public Result<MessageBoard> get(@PathVariable String id){
        return new ResultUtil<MessageBoard>().setData(iMessageBoardService.getById(id));
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有留言")
    public Result<List<MessageBoard>> getAll(){
        return new ResultUtil<List<MessageBoard>>().setData(iMessageBoardService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询留言")
    public Result<IPage<MessageBoard>> getByPage(@ModelAttribute MessageBoard board,@ModelAttribute PageVo page){
        QueryWrapper<MessageBoard> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(board.getDate())) {
            qw.eq("date",board.getDate());
        }
        if(ZwzNullUtils.isNull(board.getReplyId())) {
            qw.eq("reply_id","");
        }
        else {
            qw.eq("reply_id",board.getReplyId());
        }
        return new ResultUtil<IPage<MessageBoard>>().setData(iMessageBoardService.page(PageUtil.initMpPage(page),qw));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增留言")
    public Result<MessageBoard> insert(@RequestParam String content){
        if(ZwzNullUtils.isNull(content)) {
            return ResultUtil.error("留言不能为空");
        }
        User currUser = securityUtil.getCurrUser();
        MessageBoard messageBoard = new MessageBoard();
        messageBoard.setUserId(currUser.getId());
        messageBoard.setUserName(currUser.getNickname());
        messageBoard.setContent(content);
        messageBoard.setDate(getZwzNowTime());
        messageBoard.setReplyId("");
        if(iMessageBoardService.saveOrUpdate(messageBoard)){
            return new ResultUtil<MessageBoard>().setData(messageBoard);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insertReply", method = RequestMethod.POST)
    @ApiOperation(value = "新增留言回复")
    public Result<MessageBoard> insertReply(@RequestParam String content, @RequestParam String replyId){
        User currUser = securityUtil.getCurrUser();
        MessageBoard mb = iMessageBoardService.getById(replyId);
        if(mb == null) {
            return ResultUtil.error("回复留言已被删除");
        }
        MessageBoard messageBoard = new MessageBoard();
        messageBoard.setUserId(currUser.getId());
        messageBoard.setUserName(currUser.getNickname());
        messageBoard.setDate(getZwzNowTime());
        messageBoard.setReplyId(replyId);
        messageBoard.setContent(content);
        if(iMessageBoardService.saveOrUpdate(messageBoard)){
            return new ResultUtil<MessageBoard>().setData(messageBoard);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑留言")
    public Result<MessageBoard> update(MessageBoard messageBoard){
        if(iMessageBoardService.saveOrUpdate(messageBoard)){
            return new ResultUtil<MessageBoard>().setData(messageBoard);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除留言")
    public Result<Object> delAllByIds(@RequestParam String[] ids){
        for(String id : ids){
            iMessageBoardService.removeById(id);
        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取当前完整日期时间")
    public static String getZwzNowTime() {
        Date date = new Date();
        int hour = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date1 = date.getDate();
        return "" + year + "-" + (month < 10 ? "0" + month : month) + "-" + (date1 < 10 ? "0" + date1 : date1)
                + " " + hour + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }
}
