package com.rq.manager.authusers.service;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.mapper.UserMapper;
import com.rq.manager.authusers.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * The Class AuthService.
 */
@Service
@AllArgsConstructor
public class AuthService {

	/** The user repository. */
	private final UserRepository userRepository;
	
	/**
	 * Register user.
	 *
	 * @param register The value of the new user
	 * @return the new user
	 */
	public User registerUser(Register register) {
		if (userRepository.existsByEmail(register.getEmail())) {
			throw new IllegalArgumentException();
		}

		if (userRepository.existsByUsername(register.getUsername())) {
			throw new IllegalArgumentException();
		}
		User user = UserMapper.mapRegisterUser(register);
		userRepository.save(user);
		return user;
	}
	
	/**
	 * Authenticate user in the login.
	 *
	 * @param login the credentials for open
	 * @return the user
	 */
	public User authenticateUser(Login login) {
	    return userRepository.findUser(login.getEmail(), login.getPassword())
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}
}
