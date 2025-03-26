package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.entity.Rol;
import com.rq.manager.authusers.entity.User;

public class UserMapper {
	
	/** The password encoder. */
//	protected static PasswordEncoder passwordEncoder;
	
	public static User mapRegisterEntity(Register register, Rol rol) {
		User user = new User();
		user.setEmail(register.getEmail());
//		user.setPassword(passwordEncoder.encode(register.getPassword()));
		user.setPassword(register.getPassword());
		user.setName(register.getName());
		user.setLastname(register.getLastname());
		user.setUsername(register.getUsername());
		user.setNumPhone(register.getNumPhone());
		user.setRol(rol);
		return user;
	}
	
	public static UserResponse mapEntityUserResponse(User user) {
		return UserResponse.builder().id(user.getId()).email(user.getEmail())
				.name(user.getName()).lastname(user.getLastname())
				.password(user.getPassword()).username(user.getUsername())
				.numPhone(user.getNumPhone()).rol(user.getRol()).build();
	}

}
