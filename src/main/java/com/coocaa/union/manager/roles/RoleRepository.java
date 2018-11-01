package com.coocaa.union.manager.roles;

import com.coocaa.union.manager.NoCudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends NoCudRepository<Role, Integer> {

    //Page<Role> findByRoleKeyContainsAndNameContainsAndStatus(@Param("roleKey")String roleKey,
     //                                                         @Param("name") String name,
     //                                                         @Param("status")Integer status, Pageable pageable);

    List<Role> findByStatus(@Param("status")Integer status);

}
