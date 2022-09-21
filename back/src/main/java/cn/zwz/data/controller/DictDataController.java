package cn.zwz.data.controller;

import cn.zwz.basics.log.LogType;
import cn.zwz.basics.log.SystemLog;
import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.data.entity.Dict;
import cn.zwz.data.entity.DictData;
import cn.zwz.data.service.IDictDataService;
import cn.zwz.data.service.IDictService;
import cn.zwz.data.utils.ZwzNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 数据字典值
 * @author 郑为中
 */
@RestController
@RequestMapping("/zwz/dictData")
@Api(tags = "字典数据值")
@Transactional
public class DictDataController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IDictDataService iDictDataService;

    private static final String REDIS_DIST_DATA_PRE_STR = "dictData::";

    @SystemLog(about = "查询单个数据字典的值", type = LogType.DATA_CENTER,doType = "DICT_DATA-01")
    @RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个数据字典的值")
    public Result<Object> getByType(@PathVariable String type){
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq("type",type);
        Dict selectDict = iDictService.getOne(qw);
        if (selectDict == null) {
            return ResultUtil.error("字典 "+ type +" 不存在");
        }
        QueryWrapper<DictData> dataQw = new QueryWrapper<>();
        dataQw.eq("dict_id",selectDict.getId());
        return ResultUtil.data(iDictDataService.list(dataQw));
    }

    @SystemLog(about = "查询数据字典值", type = LogType.DATA_CENTER,doType = "DICT_DATA-02")
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "查询数据字典值")
    public Result<IPage<DictData>> getByCondition(@ModelAttribute DictData dictData, @ModelAttribute PageVo page) {
        QueryWrapper<DictData> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(dictData.getDictId())) {
            qw.eq("dict_id",dictData.getDictId());
        }
        if(!Objects.equals(null,dictData.getStatus())) {
            qw.eq("status",dictData.getStatus());
        }
        if(!ZwzNullUtils.isNull(dictData.getTitle())) {
            qw.like("title",dictData.getTitle());
        }
        if(!ZwzNullUtils.isNull(dictData.getValue())) {
            qw.like("value",dictData.getValue());
        }
        if(!ZwzNullUtils.isNull(dictData.getDescription())) {
            qw.like("description",dictData.getDescription());
        }
        IPage<DictData> data = iDictDataService.page(PageUtil.initMpPage(page),qw);
        for (DictData dd : data.getRecords()) {
            if(dd != null) {
                Dict dict = iDictService.getById(dd.getDictId());
                if(dict != null) {
                    dd.setDictName(dict.getTitle());
                }
            }
        }
        return new ResultUtil<IPage<DictData>>().setData(data);
    }

    @SystemLog(about = "删除数据字典值", type = LogType.DATA_CENTER,doType = "DICT_DATA-03")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除数据字典值")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String dictDataId : ids){
            DictData dictData = iDictDataService.getById(dictDataId);
            Dict dict = iDictService.getById(dictData.getDictId());
            iDictDataService.removeById(dictDataId);
            redisTemplate.delete(REDIS_DIST_DATA_PRE_STR + dict.getType());
        }
        return ResultUtil.success();
    }

    @SystemLog(about = "添加数据字典值", type = LogType.DATA_CENTER,doType = "DICT_DATA-04")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加数据字典值")
    public Result<Object> add(DictData dictData){
        Dict selectDict = iDictService.getById(dictData.getDictId());
        if (selectDict == null) {
            return ResultUtil.error("字典不存在");
        }
        iDictDataService.saveOrUpdate(dictData);
        redisTemplate.delete(REDIS_DIST_DATA_PRE_STR + selectDict.getType());
        return ResultUtil.success();
    }

    @SystemLog(about = "编辑数据字典值", type = LogType.DATA_CENTER,doType = "DICT_DATA-05")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑数据字典值")
    public Result<Object> edit(DictData dictData){
        iDictDataService.saveOrUpdate(dictData);
        Dict selectDict = iDictService.getById(dictData.getDictId());
        redisTemplate.delete(REDIS_DIST_DATA_PRE_STR + selectDict.getType());
        return ResultUtil.success();
    }
}
