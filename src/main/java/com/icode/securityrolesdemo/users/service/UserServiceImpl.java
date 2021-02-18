package com.icode.securityrolesdemo.users.service;

import com.icode.securityrolesdemo.users.entity.UserEntity;
import com.icode.securityrolesdemo.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

 		@Override
    public UserEntity addUser(UserEntity userEntity){
        modifyUser(userEntity);
        try{
            return userRepository.save(userEntity);
        }catch(Exception ex){
            throw new ConstraintViolationException(ex.getMessage(), null, "");
        }
    }
    @Override
    public int getUserId(String username) {
        return userRepository.findByUsernameIgnoreCase(username.toLowerCase()).get().getId();
    }

    @Override
    public UserEntity getUser(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    private void modifyUser(UserEntity originalUser){
        originalUser.setPassword(encoder.encode(originalUser.getPassword()));
        originalUser.setEmail(originalUser.getEmail().toLowerCase());
    }
}

