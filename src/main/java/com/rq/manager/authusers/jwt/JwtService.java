package com.rq.manager.authusers.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * The Class JwtUtil.
 */
@Service
public class JwtService {

    /** The secret jwt. */
    @Value("${jwt.secret}")
    private String secretJwt;

    /** The time expiration of token (in milliseconds). */
    @Value("${jwt.expiration}")
    private long expiration; 
    
    /** The key. */
    private SecretKey key;
    
    public JwtService() {
	}
    
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretJwt.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generar token mediante la authenticacion del usuario.
     *
     * @param authentication the authentication of the user
     * @return the token
     */
    public String generarToken(Authentication authentication) {
        UserDetails mainUser = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(mainUser.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Usa el valor de application.properties
                .signWith(key)
                .compact();
    }

    /**
     * Validate token.
     *
     * @param token the token
     * @param userDetails the user details
     * @return true, if successful
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpirated(token));
    }

    /**
     * Extract user name.
     *
     * @param token the token
     * @return the claims of the token
     */
    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Checks if token is expired.
     *
     * @param token the token
     * @return true if the token is expired
     */
    public boolean isTokenExpirated(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract expiration date from token.
     *
     * @param token the token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Extract all claims from token.
     *
     * @param token the token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Usa la misma clave
                .build()
                .parseClaimsJws(token) // Usa parseClaimsJws() en vez de parseClaimsJwt()
                .getBody();
    }

}
