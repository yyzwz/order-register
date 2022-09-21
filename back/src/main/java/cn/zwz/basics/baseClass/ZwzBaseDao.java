package cn.zwz.basics.baseClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 基类数据链路层
 * @author 郑为中
 */
@NoRepositoryBean
public interface ZwzBaseDao<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    @Override
    E getById(ID id);
}
