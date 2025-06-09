package com.rq.manager.authusers.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.Credentials;
import com.rq.manager.authusers.bean.FormPassword;
import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.UpdateUserInfoRequest;
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
	 * @param formPassword the form password
	 * @return the user response
	 */
	public void changePassword(FormPassword formPassword) {
	    // Obtener el usuario autenticado desde la base de datos
	    User user = userRepository.findByUsername(
	            SecurityContextHolder.getContext().getAuthentication().getName())
	        .orElseThrow(() -> new CustomException(ErrorConstants.NULL_USER));

	    // Validar la contraseña antigua
	    if (!passwordEncoder.matches(formPassword.getOldPassword(), user.getPassword())) {
	        throw new CustomException("La contraseña actual es incorrecta");
	    }

	    // Validar que las nuevas contraseñas coincidan
	    if (!formPassword.getNewPassword().equals(formPassword.getVerifyNewPassword())) {
	        throw new CustomException("Las nuevas contraseñas no coinciden");
	    }

	    // Guardar la nueva contraseña encriptada
	    user.setPassword(passwordEncoder.encode(formPassword.getNewPassword()));
	    userRepository.save(user);
	}

	/**
     * Actualiza los datos personales del usuario autenticado
     * (email, name, lastname, username, numPhone). 
     * No modifica puntos ni contraseña.
     */
    @Transactional
    public UserResponse updateMyInfo(UpdateUserInfoRequest req) {
        // 1) Cargar usuario actual
        User user = userRepository.findByUsername(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName())
                .orElseThrow(
                        () -> new CustomException(ErrorConstants.NULL_USER));
        // 2) Email
        if (req.getEmail() != null && !req.getEmail().isBlank()
                && !req.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(req.getEmail())) {
                throw new CustomException(ErrorConstants.EMAIL_ALREADY_IN_USE);
            }
            user.setEmail(req.getEmail());
        }

        // 3) Username
        if (req.getUsername() != null && !req.getUsername().isBlank()
                && !req.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(req.getUsername())) {
                throw new CustomException(
                        ErrorConstants.USERNAME_ALREADY_IN_USE);
            }
            user.setUsername(req.getUsername());
        }

        // 4) Número de teléfono
        if (req.getNumPhone() != null && !req.getNumPhone().isBlank()
                && !req.getNumPhone().equals(user.getNumPhone())) {
            if (userRepository.existsByNumPhone(req.getNumPhone())) {
                throw new CustomException(ErrorConstants.PHONE_ALREADY_IN_USE);
            }
            user.setNumPhone(req.getNumPhone());
        }
        if (req.getName() != null && !req.getName().isBlank()) {
            user.setName(req.getName());
        }
        if (req.getLastname() != null && !req.getLastname().isBlank()) {
            user.setLastname(req.getLastname());
        }
        // 3) Guardar cambios
        userRepository.save(user);
        // 4) Devolver DTO
        return UserMapper.mapEntityToResponse(user);
    }

}
