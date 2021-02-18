package com.icode.securityrolesdemo.users.repository;


import com.icode.securityrolesdemo.users.entity.RolesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RolesEntity, Integer> {

}
