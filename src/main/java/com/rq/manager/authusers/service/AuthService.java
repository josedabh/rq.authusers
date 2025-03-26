package com.rq.manager.authusers.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.entity.Rol;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
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
	public UserResponse registerUser(Register register) {
		if (userRepository.existsByEmail(register.getEmail())) {
			throw new CustomException(ErrorConstants.NULL_USER);
		}

		if (userRepository.existsByUsername(register.getUsername())) {
			throw new CustomException(ErrorConstants.NULL_USER);
		}
		User user = UserMapper.mapRegisterEntity(register, Rol.NORMAL);
		userRepository.save(user);
		return UserMapper.mapEntityUserResponse(user);
	}
	
	/**
	 * Authenticate user in the login.
	 *
	 * @param login the credentials for open
	 * @return the user
	 */
	public User authenticateUser(Login login) {
	    return userRepository.findByUsernameOrEmailOrNumPhoneAndPassword(
	                    login.getIdentifier(), 
	                    login.getIdentifier(), 
	                    login.getIdentifier(), 
	                    login.getPassword()
	            )
	            .orElseThrow(() -> new CustomException(ErrorConstants.ERROR_CREDENTIALS));
	}

	
	
	
	//cONTROLAR EL TIEMPOO QUE TENDRA DE PREMIUM 
	//O SI NO NO TIENE SENTIDO EL METODO
	public User payPremium(UUID id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException());
		user.setRol(Rol.PREMIUM);
		return user;
	}
}
