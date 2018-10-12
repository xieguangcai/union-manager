package com.coocaa.union.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseServiceImpl<T,ID> implements BaseService<T,ID> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract CrudRepository<T,ID> getCrudRepostory();

    @Override
    public void save(T item) {
        getCrudRepostory().save(item);
    }

    @Transactional
    @Override
    public void deleteById(ID[] ids) {
        for (ID id:ids) {
            getCrudRepostory().deleteById(id);
        }
    }
}
