package com.icode.securityrolesdemo.security.model;

public class UserAuthenticateResponse {

    private final String jwt;

    public UserAuthenticateResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
