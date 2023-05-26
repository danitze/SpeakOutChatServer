package com.coursework.speakoutchat.pairingservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private static final String SECRET = "6250655368566D597133743677397A244226452948404D635166546A576E5A72";

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBites = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBites);
    }
}
