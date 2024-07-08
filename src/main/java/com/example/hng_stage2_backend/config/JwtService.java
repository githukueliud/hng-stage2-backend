package com.example.hng_stage2_backend.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {
    public static final String SECRET = "C5091D0AE6EF5C1F571D41B90C5AF35FBAD66CB1C71092133C055C8945456AA28733D37DEDDFC67854D1A627A65922B1B78E83D41FAACEF7AF5A6C500CED4739";

    public static final Long VALIDITY = TimeUnit.MINUTES.toMillis(30);



    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }


    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {


        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }


//    public boolean isTokenExpired(String jwtToken) {
//        return extractExpiration(jwtToken).before(new Date());
//    }


    public boolean isTokenExpired(String jwtToken) {
        try {
            return extractExpiration(jwtToken).before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // Token is expired
        }
    }


    public Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }



    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }


    public SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
