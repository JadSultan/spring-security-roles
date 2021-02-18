package com.icode.securityrolesdemo.security.service;

import com.icode.securityrolesdemo.users.entity.RolesEntity;
import com.icode.securityrolesdemo.users.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class MyUserDetails implements UserDetails {

    private UserEntity userEntity;

    public MyUserDetails() {
    }

    public MyUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<RolesEntity> roles = userEntity.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(RolesEntity role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !userEntity.isTokenExpired();
    }

    @Override
    public boolean isEnabled() {
        return userEntity.isEnabled();
    }

    public int getId(){
        return this.userEntity.getId();
    }
}
