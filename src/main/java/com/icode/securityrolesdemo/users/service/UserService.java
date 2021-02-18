package com.icode.securityrolesdemo.users.service;



import com.icode.securityrolesdemo.users.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity addUser(UserEntity userEntity);

    UserEntity getUser(int id);

    int getUserId(String username);

    void deleteUser(int id);
}
