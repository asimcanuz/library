package com.asodev.library.controller;

import com.asodev.library.dto.LoginRequest;
import com.asodev.library.dto.SignupRequest;
import com.asodev.library.model.User;
import com.asodev.library.service.JwtService;
import com.asodev.library.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/addNewUser")
    public User addUser(@RequestBody SignupRequest request){
        return userService.createUser(request);
    }
    @PostMapping("/generateToken")
    public String generateToken(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        System.out.println(authentication);
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(request.username());
            System.out.println("token:" + token);
            return token;
        }
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

}
