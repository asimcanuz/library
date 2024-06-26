package com.asodev.library.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.key}")
    private String SECRET;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username,jwtExpiration);
    }
    public String generateRefreshToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username,refreshExpiration);
    }




    public Boolean validateToken(String token, UserDetails userDetails){
        String username = extractUser(token);
        Date expirationDate = extractExpiration(token);
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
    }
    public Date extractExpiration(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    public String extractUser(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String createToken(Map<String, Object> claims, String userName, long expiration){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration ))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        // decode jwt secret key
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
