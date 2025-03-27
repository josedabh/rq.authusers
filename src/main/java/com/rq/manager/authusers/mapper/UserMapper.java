package com.rq.manager.authusers.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.entity.Rol;
import com.rq.manager.authusers.entity.User;

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
    public static User mapRegisterEntity(Register register, Rol rol) {
        User user = new User();
        user.setEmail(register.getEmail());
        user.setPassword(encoder.encode(register.getPassword()));
        user.setName(register.getName());
        user.setLastname(register.getLastname());
        user.setUsername(register.getUsername());
        user.setNumPhone(register.getNumPhone());
        user.setRol(rol);
        return user;
    }
	
	/**
	 * Map entity user response.
	 *
	 * @param user the user
	 * @return the user response
	 */
	public static UserResponse mapEntityUserResponse(User user) {
		return UserResponse.builder().id(user.getId()).email(user.getEmail())
				.name(user.getName()).lastname(user.getLastname())
				.password(user.getPassword()).username(user.getUsername())
				.numPhone(user.getNumPhone()).rol(user.getRol()).build();
	}

}
