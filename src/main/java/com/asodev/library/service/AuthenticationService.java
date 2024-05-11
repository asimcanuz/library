package com.asodev.library.service;

import com.asodev.library.dto.AuthenticationResponse;
import com.asodev.library.dto.LoginRequest;
import com.asodev.library.dto.SignupRequest;
import com.asodev.library.model.Token;
import com.asodev.library.model.TokenType;
import com.asodev.library.model.User;
import com.asodev.library.repository.TokenRepository;
import com.asodev.library.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AuthenticationResponse register(SignupRequest request) {
        User newUser = new User.Builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .password(bCryptPasswordEncoder.encode(request.password()))
                .authorities(request.roles())
                .build();

        var savedUser = userRepository.save(newUser);
        var jwtToken = jwtService.generateToken(savedUser.getUsername());
        var refreshToken = jwtService.generateRefreshToken(savedUser.getUsername());
        saveUserToken(savedUser, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken, savedUser.getUsername(), savedUser.getAuthorities());
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = userRepository.findByUsername(request.username()).orElseThrow();
        var jwtToken = jwtService.generateToken(user.getUsername());
        var refreshToken = jwtService.generateRefreshToken(user.getUsername());

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken, user.getUsername(), user.getAuthorities());
    }


    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setTokenType(TokenType.BEARER);
        token.setToken(jwtToken);
        token.setRevoked(false);
        token.setExpired(false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    // Add the refreshToken method here



    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUser(refreshToken);
        if (username != null) {
            var user = userRepository.findByUsername(username).orElseThrow();
            if (jwtService.validateToken(refreshToken, user)) {
                var accessToken = jwtService.generateToken(username);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthenticationResponse(accessToken, refreshToken,username, user.getAuthorities());
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

        }


    }
}
