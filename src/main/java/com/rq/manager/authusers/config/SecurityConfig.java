package com.rq.manager.authusers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rq.manager.authusers.constants.ApiConstants;
import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.jwt.JwtAuthenticationFilter;
import com.rq.manager.authusers.jwt.JwtEntryPoint;

/**
 * The Class SecurityConfig.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//Averiguaur OAuth2.0 para implementar el registro de sesión de google y discord
	
	/** The port. */
	@Value("${server.port}")
	private String port;
	
	/**
	 * Filter chain.
	 *
	 * @param http the http
	 * @return the security filter chain
	 * @throws Exception the exception
	 */
	//Quitar el metodo hello
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> 
				auth.requestMatchers(Constants.REQUEST_LOGIN , Constants.REQUEST_REGISTER,
						ApiConstants.SWAGGER_UI, ApiConstants.API_DOCS, "/api/v1/auth/hello")
						.permitAll()
						.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint()))
				.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
//	@Bean
//	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    http.csrf(AbstractHttpConfigurer::disable)
//	        .cors(Customizer.withDefaults())
//	        .authorizeHttpRequests(auth -> auth
//	            .anyRequest().permitAll())
//	        .httpBasic(Customizer.withDefaults());
//	    return http.build();
//	}
	
	/**
	 * Jwt token filter.
	 *
	 * @return the jwt authentication filter
	 */
	@Bean
	public JwtAuthenticationFilter jwtTokenFilter() {
	    return new JwtAuthenticationFilter();
	}
	
	/**
	 * Jwt entry point.
	 *
	 * @return the jwt entry point
	 */
	@Bean 
	public JwtEntryPoint jwtEntryPoint() {
		return new JwtEntryPoint();
	}
	
	/**
	 * Password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Cors configuration source.
	 *
	 * @return the cors configuration source
	 */
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//
//		//Cambiar esto a la url del front si n o me falla
//		configuration.setAllowedOrigins(List.of("*"));
//		//Cambiar esto si hago mas metodos
//		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//		configuration.setAllowedHeaders(List.of(Constants.AUTHORIZATION, ApiConstants.CONTENT_TYPE));
//		configuration.setAllowCredentials(true);
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}

}