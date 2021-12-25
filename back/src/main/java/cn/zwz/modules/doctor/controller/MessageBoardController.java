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
import cn.hutool.core.util.StrUtil;
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
@Api(description = "留言板管理接口")
@RequestMapping("/zwz/messageBoard")
@Transactional
public class MessageBoardController {

    @Autowired
    private IMessageBoardService iMessageBoardService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<MessageBoard> get(@PathVariable String id){

        MessageBoard messageBoard = iMessageBoardService.getById(id);
        return new ResultUtil<MessageBoard>().setData(messageBoard);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<MessageBoard>> getAll(){

        List<MessageBoard> list = iMessageBoardService.list();
        return new ResultUtil<List<MessageBoard>>().setData(list);
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
        IPage<MessageBoard> data = iMessageBoardService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<MessageBoard>>().setData(data);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增留言")
    public Result<MessageBoard> insert(@RequestParam String content){
        if(ZwzNullUtils.isNull(content)) {
            return new ResultUtil<MessageBoard>().setErrorMsg("留言不能为空");
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
        return new ResultUtil<MessageBoard>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/insertReply", method = RequestMethod.POST)
    @ApiOperation(value = "新增留言回复")
    public Result<MessageBoard> insertReply(@RequestParam String content, @RequestParam String replyId){
        User currUser = securityUtil.getCurrUser();
        MessageBoard mb = iMessageBoardService.getById(replyId);
        if(mb == null) {
            return new ResultUtil<MessageBoard>().setErrorMsg("回复留言已被删除");
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
        return new ResultUtil<MessageBoard>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑留言")
    public Result<MessageBoard> update(MessageBoard messageBoard){

        if(iMessageBoardService.saveOrUpdate(messageBoard)){
            return new ResultUtil<MessageBoard>().setData(messageBoard);
        }
        return new ResultUtil<MessageBoard>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids){

        for(String id : ids){
            iMessageBoardService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }

    /**
     * 获取当前完整日期时间
     * @return
     */
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
