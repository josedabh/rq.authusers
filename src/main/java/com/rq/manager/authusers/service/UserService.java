package com.rq.manager.authusers.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.repository.UserRepository;

import lombok.NoArgsConstructor;

/**
 * The Class UserService that manage the tokens.
 */
@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {
	
	/** The user repository. */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username"));
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRol().toString());
		return new org.springframework.security.core.userdetails.User(
				user.getName(),
				user.getPassword(),
				Collections.singleton(authority));
	}
}
