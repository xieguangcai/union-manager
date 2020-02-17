package com.coocaa.union.manager.roles;

import com.coocaa.union.manager.NoCudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends NoCudRepository<Role, Integer> {

    List<Role> findByStatus(@Param("status")Integer status);

}
