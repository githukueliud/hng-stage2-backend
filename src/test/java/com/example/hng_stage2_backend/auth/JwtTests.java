package com.example.hng_stage2_backend.auth;

import com.example.hng_stage2_backend.config.JwtService;
import com.example.hng_stage2_backend.user.entity.MyUserDetails;
import com.example.hng_stage2_backend.user.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTests {
    @InjectMocks
    private JwtService jwtService;

    private MyUserDetails myUserDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setEmail("eliud@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhone("1234567890");

        myUserDetails = new MyUserDetails(user);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken(myUserDetails);

        assertNotNull(token);
        assertDoesNotThrow(() -> Jwts.parser().setSigningKey(jwtService.generateKey()).build().parseClaimsJws(token));
    }

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken(myUserDetails);
        String username = jwtService.extractUsername(token);

        assertEquals(myUserDetails.getUsername(), username);
    }

    @Test
    public void testTokenExpiration() {
        String token = jwtService.generateToken(myUserDetails);
        Date expiration = jwtService.extractExpiration(token);

        Date expectedExpiration = Date.from(Instant.now().plusMillis(JwtService.VALIDITY));
        long timeDifference = Math.abs(expiration.getTime() - expectedExpiration.getTime());

        // Increase the margin to 5 seconds
        assertTrue(timeDifference < 5000, "Token expiration time should be within 5 seconds of expected expiration time");
    }

    @Test
    public void testIsTokenValid() {
        String token = jwtService.generateToken(myUserDetails);

        assertTrue(jwtService.isTokenValid(token, myUserDetails));
    }

    @Test
    public void testIsTokenExpired() {
        // Create a token that expires in the past
        String token = Jwts.builder()
                .setSubject(myUserDetails.getUsername())
                .setExpiration(Date.from(Instant.now().minusMillis(TimeUnit.MINUTES.toMillis(1))))
                .signWith(jwtService.generateKey(), SignatureAlgorithm.HS512)
                .compact();

        // Check if the token is expired
        assertTrue(jwtService.isTokenExpired(token));
    }

}
