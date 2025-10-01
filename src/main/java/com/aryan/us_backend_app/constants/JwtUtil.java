package com.aryan.us_backend_app.constants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.aryan.us_backend_app.model.RoomModel;
import com.aryan.us_backend_app.model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private static final String LOGIN_TOKEN_SECRET_KEY = "poiuygvdsxghjjnbvfdcsxcvghjhgfcdcxdfghjkjnbvfdsdcdswsxcfghjmnbgfcdsdxfghjnbvfcdd";
    private static final Long EXPRI_LONG = 1000L * 60 * 60 * 1; // 1 hour
    private static final Key key = Keys.hmacShaKeyFor(LOGIN_TOKEN_SECRET_KEY.getBytes());

    public static String generateLoginToken(UserModel user , RoomModel room) {
         Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.username);
        claims.put("userType", user.userType);
        claims.put("roomName", room.roomName);
        claims.put("userId", user.userId);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPRI_LONG))
                .signWith(key)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
