package com.coocaa.union.manager;

import com.coocaa.union.manager.accounts.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BaseService<T, ID> {
    void save(T item);

    void deleteById(ID[] ids);
}
