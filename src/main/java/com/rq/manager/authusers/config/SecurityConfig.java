package com.rq.manager.authusers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Deshabilita CSRF
				.cors(cors -> cors.disable()) // Deshabilita CORS (opcional)
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll() // Permite todas las solicitudes sin
				);
		return http.build();
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.invalidSessionUrl("/login?expired=true").sessionFixation().migrateSession().maximumSessions(2)
				.expiredUrl("/login?duplicate=true"));
	}

//	@Override
//	  protected void configure(HttpSecurity http) throws Exception \{
//	    http
//	      .authorizeRequests()
//	        .antMatchers("/").permitAll()
//	        .anyRequest().authenticated()
//	        .and()
//	      .oauth2Login()
//	        .loginPage("/login")
//	        .defaultSuccessUr1("/home")
//	        .userInfoEndpoint()
//	        .userService(oAuth2UserService());
//	  }
//
//	  @Bean
//	  public OAuth2UserService<OAuth2UserRequest, OAuth2User> OAuth2UserService() {
//	    return new DefaultOAuth2UserService();
//	  }
}