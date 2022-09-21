package cn.zwz.basics.baseClass;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 基类控制器
 * @author 郑为中
 */
public abstract class ZwzBaseController<E, ID extends Serializable> {

    @Autowired
    public abstract ZwzBaseService<E,ID> getZwzService();

    @RequestMapping(value = "/getOne", name = "查询单个数据", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询单个数据")
    public Result<E> getOne(@RequestParam ID id){
        return new ResultUtil<E>().setData(getZwzService().get(id));
    }

    @RequestMapping(value = "/getAll", name = "查询全部数据",  method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询全部数据")
    public Result<List<E>> getAll(){
        return new ResultUtil<List<E>>().setData(getZwzService().getAll());
    }

    @RequestMapping(value = "/getByPage", name = "查询数据",  method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询数据")
    public Result<Page<E>> getByPage(PageVo page){
        return new ResultUtil<Page<E>>().setData(getZwzService().findAll(PageUtil.initPage(page)));
    }

    @RequestMapping(value = "/save", name = "新增数据",  method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增数据")
    public Result<E> save(E entity){
        return new ResultUtil<E>().setData(getZwzService().save(entity));
    }

    @RequestMapping(value = "/update", name = "编辑数据",  method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "编辑数据")
    public Result<E> update(E entity){
        return new ResultUtil<E>().setData(getZwzService().update(entity));
    }

    @RequestMapping(value = "/count", name = "查询数据条数",  method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询数据条数")
    public Result<Long> count(){
        return new ResultUtil<Long>().setData(getZwzService().count());
    }
    @RequestMapping(value = "/delOne", name = "删除数据",  method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除数据")
    public Result<Object> delByIds(@RequestParam ID id){
        getZwzService().delete(id);
        return new ResultUtil<Object>().setSuccessMsg("OK");
    }

    @RequestMapping(value = "/delByIds", name = "删除数据",  method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除数据")
    public Result<Object> delByIds(@RequestParam ID[] ids){
        for(ID id:ids){
            getZwzService().delete(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("OK");
    }
}
