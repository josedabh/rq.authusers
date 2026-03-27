package com.rq.manager.authusers.mapper;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.admin.UserResponse;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.enumerations.RolEnum;

/**
 * The Class UserMapper.
 */
public class UserMapper {

    /** The Constant encoder. */
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Instantiates a new user mapper.
     */
    private UserMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Map register entity.
     *
     * @param register the register
     * @param rol the rol
     * @return the user
     */
    public static User mapRegisterEntity(Register register, RolEnum rol) {
        User user = new User();
        user.setEmail(register.getEmail());
        user.setPassword(encoder.encode(register.getPassword()));
        user.setName(register.getName());
        user.setLastname(register.getLastname());
        user.setUsername(register.getUsername());
        user.setNumPhone(register.getNumPhone());
        user.setPoints(0);
        user.setRol(rol);
        return user;
    }

    /**
     * Map entity user response.
     *
     * @param user the user
     * @return the user response
     */
    public static UserResponse mapEntityToResponse(User user) {
        return UserResponse.builder().id(user.getId().toString())
                .email(user.getEmail()).name(user.getName())
                .lastname(user.getLastname())
                .username(user.getUsername()).numPhone(user.getNumPhone())
                .points(user.getPoints()).rol(user.getRol().getCodigo())
                .build();
    }

	/**
	 * Map user response to entity.
	 *
	 * @param user the user
	 * @return the user
	 */
	public static User mapUserResponseToEntity(UserResponse user) {
		User userEntity = new User();
		userEntity.setId(UUID.fromString(user.getId()));
		userEntity.setEmail(user.getEmail());
		userEntity.setName(user.getName());
		userEntity.setLastname(user.getLastname());
		userEntity.setUsername(user.getUsername());
		userEntity.setNumPhone(user.getNumPhone());
		userEntity.setPoints(user.getPoints());
		userEntity.setRol(user.getRol().equals("ADMIN") ? RolEnum.ADMIN : RolEnum.NORMAL);
		return userEntity;
	}
}
