package cn.zwz.modules.file.serviceimpl;

import cn.zwz.common.vo.SearchVo;
import cn.zwz.modules.file.dao.FileDao;
import cn.zwz.modules.file.entity.File;
import cn.zwz.modules.file.service.FileService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件管理接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    @Override
    public FileDao getRepository() {
        return fileDao;
    }

    @Override
    public Page<File> findByCondition(File file, SearchVo searchVo, Pageable pageable) {

        return fileDao.findAll(new Specification<File>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<File> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> nameField = root.get("name");
                Path<String> fKeyField = root.get("fKey");
                Path<String> typeField = root.get("type");
                Path<String> createByField = root.get("createBy");
                Path<Integer> locationField = root.get("location");
                Path<Date> createTimeField=root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                //模糊搜素
                if(StrUtil.isNotBlank(file.getName())){
                    list.add(cb.like(nameField,'%'+file.getName()+'%'));
                }
                if(StrUtil.isNotBlank(file.getFKey())){
                    list.add(cb.like(fKeyField,'%'+file.getFKey()+'%'));
                }
                if(StrUtil.isNotBlank(file.getType())){
                    list.add(cb.like(typeField,'%'+file.getType()+'%'));
                }
                if(StrUtil.isNotBlank(file.getCreateBy())){
                    list.add(cb.like(createByField,'%'+file.getCreateBy()+'%'));
                }

                if(file.getLocation()!=null){
                    list.add(cb.equal(locationField, file.getLocation()));
                }

                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }
}