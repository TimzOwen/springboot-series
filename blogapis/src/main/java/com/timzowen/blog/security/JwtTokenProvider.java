package com.timzowen.blog.security;

import com.timzowen.blog.exceptions.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("app.jwt-secret")
    private String jwtSecret;
    @Value("app-jwt-expiration-milliseconds")
    private long jwtExpirationDate;

    // step - 001 -> Generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getKey())
                .compact();
        return token;
    }

    // step 001: --> build the decoder to decode the decode secret Key
    public Key getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // 004 --> validate JWT token generated
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) getKey())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"JWT should not be null or empty");
        }catch (ExpiredJwtException expiredJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired JWT token");
        }catch (UnsupportedJwtException unsupportedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"unsupported JWT token");
        }
    }
}
