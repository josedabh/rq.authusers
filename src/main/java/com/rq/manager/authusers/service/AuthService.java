package com.rq.manager.authusers.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.Credentials;
import com.rq.manager.authusers.bean.FormPassword;
import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.admin.UserResponse;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
import com.rq.manager.authusers.jwt.JwtService;
import com.rq.manager.authusers.mapper.UserMapper;
import com.rq.manager.authusers.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * The Class AuthService.
 */
@Service @AllArgsConstructor
public class AuthService {

    /** The password encoder. */
    private final PasswordEncoder passwordEncoder;

    /** The user repository. */
    private UserRepository userRepository;

    /** The jwt util. */
    private JwtService jwtService;

    /** The authentication manager builder. */
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    /** The user challenge repository. */
//	private UserChallengeRepository userChallengeRepository;

    /**
     * Register user.
     *
     * @param register The value of the new user
     * @return the new user
     */
    public Credentials registerUser(Register register) {
        // Verificamos que el email y el username no existan
        // Cambiar el mensaje del throw
        if (userRepository.existsByEmail(register.getEmail())
                || userRepository.existsByUsername(register.getUsername())) {
            throw new CustomException(ErrorConstants.NULL_USER);
        }
        User user = UserMapper.mapRegisterEntity(register, RolEnum.NORMAL);
        userRepository.save(user);
        // Devolvemos el token al usuario
        return createToken(user.getUsername(), register.getPassword());
    }

    /**
     * Creates the token.
     *
     * @param user the user
     * @param password the password
     * @return the key
     */
    private Credentials createToken(String username, String password) {
        // Creamos el token al encontrar el usuario
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generamos el token
        Credentials credentials = new Credentials();
        credentials.setToken(jwtService.generarToken(authentication));
        credentials.setAdmin(false);
        return credentials;
    }

    /**
     * Authenticate user in the login.
     *
     * @param login the credentials for open
     * @return the user
     */
    public Credentials authenticateUser(Login login) {
        User user = userRepository.findByIdentifier(login.getIdentifier())
                .orElseThrow(() -> new CustomException(
                        ErrorConstants.ERROR_CREDENTIALS));
        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorConstants.ERROR_CREDENTIALS);
        }
        // Generamos el token
        return createToken(user.getUsername(), login.getPassword());
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserResponse getUser() {
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new CustomException(ErrorConstants.NULL_USER));
		return UserMapper.mapEntityToResponse(user);
	}

    /**
     * Change password.
     *
     * @param formPassword
     *            the form password
     * @return the user response
     */
    public UserResponse changePassword(FormPassword formPassword) {
        UserResponse userResponse = getUser();
        if (isEqualsPasswords(userResponse, formPassword)) {
            User user = UserMapper.mapUserResponseToEntity(userResponse);
            user.setPassword(passwordEncoder.encode(formPassword.getNewPassword()));
            userRepository.save(user);
            return UserMapper.mapEntityToResponse(user);
        }
        return userResponse;
    }

    /**
     * Checks if is equals passwords.
     *
     * @param userResponse
     *            the user response
     * @param formPassword
     *            the form password
     * @return true, if is equals passwords
     */
    private boolean isEqualsPasswords(UserResponse userResponse,
            FormPassword formPassword) {
        return passwordEncoder.matches(userResponse.getPassword(),
                formPassword.getOldPassword())
                && formPassword.getNewPassword() != formPassword
                        .getVerifyNewPassword();
    }

//	public void historyChallenges() {
//		HistoryChallenges historyChallenges =  userChallengeRepository.findAll().stream()
//			.map(userChallenge -> UserChallengeMapper.mapEntityToResponse(userChallenge))
//				.collect(Collectors.toList());
//	}

}
