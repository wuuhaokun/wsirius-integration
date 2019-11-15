package com.wsirius.core.base;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.persistence.Id;

import com.github.pagehelper.PageHelper;
import com.wsirius.core.exception.UpdateFailedException;
import com.wsirius.core.userdetails.CustomUserDetails;
import com.wsirius.core.userdetails.DetailsHelper;
import com.wsirius.core.util.Reflections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * 基础Service实现类
 */
public abstract class BaseService<T> implements Service<T> {

    @Autowired(required = false)
    protected Mapper<T> mapper;

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        this.entityClass = Reflections.getClassGenericType(getClass());
    }

    //
    // insert
    // ----------------------------------------------------------------------------------------------------
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T insert(T record) {
        insertWhoField(record);
        mapper.insert(record);
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<T> insert(List<T> recordList) {
        for (T t : recordList) {
            insertWhoField(t);
        }
        mapper.insertList(recordList);
        return recordList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T insertSelective(T record) {
        insertWhoField(record);
        mapper.insertSelective(record);
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<T> insertSelective(List<T> recordList) {
        for (T t : recordList) {
            insertWhoField(t);
        }
        mapper.insertList(recordList);
        return recordList;
    }

    //
    // update
    // ----------------------------------------------------------------------------------------------------
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T update(T record) {
        updateWhoField(record);
        int count = mapper.updateByPrimaryKey(record);
        checkUpdate(record, count);
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<T> update(List<T> recordList) {
        // Mapper暂未提供批量更新，此处循实现
        for(T record : recordList){
            int count = mapper.updateByPrimaryKey(record);
            checkUpdate(record, count);
        }
        return recordList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T updateSelective(T record) {
        updateWhoField(record);
        int count = mapper.updateByPrimaryKeySelective(record);
        checkUpdate(record, count);
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<T> updateSelective(List<T> recordList) {
        // Mapper暂未提供批量更新，此处循实现
        for(T record : recordList){
            updateWhoField(record);
            int count = mapper.updateByPrimaryKeySelective(record);
            checkUpdate(record, count);
        }
        return recordList;
    }

    @Override
    public T updateByCondition(T record, Example condition) {
        updateWhoField(record);
        int count = mapper.updateByCondition(record, condition);
        checkUpdate(record, count);
        return record;
    }

    //
    // delete
    // ----------------------------------------------------------------------------------------------------
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long[] ids) {
        return mapper.deleteByIds(StringUtils.join(ids, ","));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(T record) {
        return mapper.delete(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<T> recordList) {
        int count = 0;
        for(T record : recordList){
            mapper.delete(record);
            count++;
        }
        return count;
    }

    //
    // select
    // ----------------------------------------------------------------------------------------------------
    @Override
    public T select(Long id) {
        T entity = null;
        try {
            entity = entityClass.newInstance();
            Field idField = Reflections.getFieldByAnnotation(entityClass, Id.class);
            idField.set(entity, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapper.selectByPrimaryKey(entity);
    }

    @Override
    public T select(T record) {
        return mapper.selectOne(record);
    }

    @Override
    public T select(String key, Object value) {
        T entity = null;
        try {
            entity = entityClass.newInstance();
            Field field = Reflections.getField(entityClass, key);
            field.set(entity, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapper.selectOne(entity);
    }

    @Override
    public List<T> selectList(Long[] ids) {
        return mapper.selectByIds(StringUtils.join(ids, ","));
    }

    @Override
    public List<T> selectList(T record) {
        return mapper.select(record);
    }

    @Override
    public List<T> selectList(String key, Object value) {
        T entity = null;
        try {
            entity = entityClass.newInstance();
            Field field = Reflections.getField(entityClass, key);
            field.set(entity, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapper.select(entity);
    }

    @Override
    public List<T> selectList(T record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.select(record);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> selectByCondition(Example condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public int count(T record) {
        return mapper.selectCount(record);
    }

    /**
     * 检查乐观锁<br>
     * 更新失败时，抛出 UpdateFailedException 异常
     *
     * @param updateCount update,delete 操作返回的值
     */
    private void checkUpdate(T record, int updateCount) {
        if (record instanceof BaseEntity && updateCount == 0) {
            throw new UpdateFailedException();
        }
    }

    /**
     * 插入标准字段
     */
    private void insertWhoField(T record) {
        if (record instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) record;
            entity.setCreateDate(new Date());
            CustomUserDetails details = DetailsHelper.getUserDetails();
            Long userId = Optional.ofNullable(details).map(CustomUserDetails::getUserId).orElse(-1L);
            entity.setCreateBy(userId);
            entity.setVersion(1L);
        }
    }

    /**
     * 更新标准字段
     */
    private void updateWhoField(T record) {
        if (record instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) record;
            entity.setUpdateDate(new Date());
            CustomUserDetails details = DetailsHelper.getUserDetails();
            Long userId = Optional.ofNullable(details).map(CustomUserDetails::getUserId).orElse(-1L);
            entity.setUpdateBy(userId);
        }
    }

}
