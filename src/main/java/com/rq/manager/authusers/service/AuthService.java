package com.rq.manager.authusers.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.entity.Rol;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
import com.rq.manager.authusers.jwt.JwtUtil;
import com.rq.manager.authusers.mapper.UserMapper;
import com.rq.manager.authusers.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * The Class AuthService.
 */
@Service
@AllArgsConstructor
public class AuthService {

    /** The password encoder. */
    private final PasswordEncoder passwordEncoder;

	/** The user repository. */
	private UserRepository userRepository;
	
	/** The jwt util. */
	private JwtUtil jwtUtil;
	
	/** The authentication manager builder. */
	private AuthenticationManagerBuilder authenticationManagerBuilder;

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
	public String authenticateUser(Login login) {
		User user = userRepository.findByIdentifier(login.getIdentifier())
					.orElseThrow(() -> 
					new CustomException(ErrorConstants.ERROR_CREDENTIALS));
		if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorConstants.ERROR_CREDENTIALS);
		}
		//Creamos el token al encontrar el usuario
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), login.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	    return jwtUtil.generarToken(authentication);
	}

}
