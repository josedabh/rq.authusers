package com.rq.manager.authusers.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * The Class JwtUtil.
 */
@Component
public class JwtUtil {

	/** The secret key. */
	@Value("${jwt.secret}")
	private String secretJwt;

	/** The time expiration of token. */
	@Value("${jwt.expiration}")
	private int expiration;

	/**
	 * Generar token mediante la authenticacion del usuario.
	 *
	 * @param authentication the authentication of the user
	 * @return the token
	 */
	public String generarToken(Authentication authentication) {
		//Extraemos de la authenticacion el usuario mediante la clase 
		//userDetails del core de spring security
		UserDetails mainUser = (UserDetails) authentication.getPrincipal();
		//Creamos la llave ya que esta deprecada la jwts
		SecretKey key = Keys.hmacShaKeyFor(secretJwt.getBytes(StandardCharsets.UTF_8)); 
		return Jwts.builder()
				.setSubject(mainUser.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 180))
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
		return (userName.equals(userDetails.getUsername()) &&
				!isTokenExpirated(token));
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
	 * Checks if is token expirated.
	 *
	 * @param token the token
	 * @return true, if is token expirated
	 */
	public boolean isTokenExpirated(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	/**
	 * Extract expiration.
	 *
	 * @param token the token
	 * @return the date of the expiration token
	 */
	private Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	
	/**
	 * Extract all claims.
	 *
	 * @param token the token
	 * @return the info the token
	 */
	private Claims extractAllClaims(String token) {
		//Creamos la llave ya que esta deprecada la jwts
		SecretKey key = Keys.hmacShaKeyFor(secretJwt.getBytes(StandardCharsets.UTF_8)); 
		return Jwts.parserBuilder().setSigningKey(key)
				.build().parseClaimsJwt(token).getBody();
	}
	
}
