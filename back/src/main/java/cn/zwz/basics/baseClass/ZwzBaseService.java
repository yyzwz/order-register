package cn.zwz.basics.baseClass;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * 基类服务层
 * @author 郑为中
 */
@FunctionalInterface
public interface ZwzBaseService<E, ID extends Serializable> {

    ZwzBaseDao<E, ID> getRepository();

    @ApiOperation(value = "查询")
    default E get(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @ApiOperation(value = "查询")
    default List<E> getAll() {
        return getRepository().findAll();
    }

    @ApiOperation(value = "新增")
    default E save(E entity) {
        return getRepository().save(entity);
    }

    @ApiOperation(value = "编辑")
    default E update(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    @ApiOperation(value = "批量保存")
    default Iterable<E> saveOrUpdateAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    @ApiOperation(value = "计数")
    default Long count() {
        return getRepository().count();
    }

    @ApiOperation(value = "删除")
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    @ApiOperation(value = "删除")
    default void delete(ID id) {
        getRepository().deleteById(id);
    }

    @ApiOperation(value = "删除")
    default void delete(Iterable<E> entities) {
        getRepository().deleteAllInBatch(entities);
    }

    @ApiOperation(value = "查询")
    default Page<E> findAll(Pageable pageable){
        return getRepository().findAll(pageable);
    }
}
