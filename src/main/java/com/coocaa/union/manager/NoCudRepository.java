package com.coocaa.union.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@NoRepositoryBean
public interface NoCudRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    @RestResource(exported = false)
    @Override
    <S extends T> List<S> saveAll(Iterable<S> iterable);

    @RestResource(exported = false)
    @Override
    <S extends T> S saveAndFlush(S s);

    @RestResource(exported = false)
    @Override
    void deleteInBatch(Iterable<T> iterable);

    @RestResource(exported = false)
    @Override
    void deleteAllInBatch();

    @RestResource(exported = false)
    @Override
    <S extends T> S save(S s);

    @RestResource(exported = false)
    @Override
    void deleteById(ID id);

    @RestResource(exported = false)
    @Override
    void delete(T t);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends T> iterable);

    @RestResource(exported = false)
    @Override
    void deleteAll();
}
