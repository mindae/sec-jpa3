package com.mindae.secjpa.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.stereotype.Service;
import java.util.Date;

import static org.yaml.snakeyaml.tokens.Token.ID.Key;

@Service
public class JwtService {
    private static final String JWT_SECRET = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecret";
    String token;
    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .setSubject(username)
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET.getBytes())).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void extractJwtComponents(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()))
                .build()
                .parseClaimsJws(token);

        System.out.println("Header: "+claimsJws.getHeader());
        System.out.println("Payload: "+claimsJws.getBody());
        System.out.println("Signature: "+claimsJws.getSignature());
    }
}
