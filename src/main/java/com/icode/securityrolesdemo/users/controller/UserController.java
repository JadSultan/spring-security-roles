package com.icode.securityrolesdemo.users.controller;

import com.icode.securityrolesdemo.security.model.UserAuthenticateRequest;
import com.icode.securityrolesdemo.security.model.UserAuthenticateResponse;
import com.icode.securityrolesdemo.security.service.MyUserDetails;
import com.icode.securityrolesdemo.security.service.MyUserDetailsService;
import com.icode.securityrolesdemo.security.util.JwtUtil;
import com.icode.securityrolesdemo.users.entity.UserEntity;
import com.icode.securityrolesdemo.users.service.UserService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserEntity userEntity){
        UserEntity addedUser;
        try{
            addedUser = userService.addUser(userEntity);
        }catch (ConstraintViolationException e){
            return ResponseEntity.badRequest().body("User or email already exists");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Entity is not valid");
        }

        final MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(addedUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new UserAuthenticateResponse(jwt));

    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserAuthenticateRequest userAuthenticateRequest) throws Exception{
        try {
            System.out.println(userAuthenticateRequest.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticateRequest.getUsername(), userAuthenticateRequest.getPassword()));
        } catch (BadCredentialsException e){
            return ResponseEntity.status(403).body("Incorrect Username or Password");
        }
        final MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(userAuthenticateRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        final int id = userService.getUserId(userAuthenticateRequest.getUsername());
        return ResponseEntity.ok(new UserAuthenticateResponse(jwt));
    }

    @GetMapping("/refreshtoken")
    public ResponseEntity refreshToken(HttpServletRequest request){
        try{

            DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");
            String username = claims.getSubject();
            final MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            System.out.println(username);
            final String jwt = jwtUtil.generateRefreshToken(userDetails);
            final int id = userService.getUserId(username);
            return ResponseEntity.ok(new UserAuthenticateResponse(jwt));
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public String test(){
        return "It is all okay you have access";
    }

    @GetMapping("/test/user")
    public String testUser(){
        return "USER Content";
    }

    @GetMapping("/test/moderator")
    public String testModerator(){
        return "moderator content";
    }

    @GetMapping("/test/admin")
    public String testAdmin(){
        return "admin content";
    }

    @GetMapping("/admin")
    public String adminContent(){
        return "<h1>ADMIN<h1>";
    }

    @GetMapping("/user")
    public String userContent(){
        return "<h1>USER<h1>";
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
    }
}
