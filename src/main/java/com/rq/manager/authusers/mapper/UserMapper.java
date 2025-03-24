package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.entity.Rol;
import com.rq.manager.authusers.entity.User;

public class UserMapper {
	
	/** The password encoder. */
//	protected static PasswordEncoder passwordEncoder;
	
	public static User mapRegisterUser(Register register) {
		User user = new User();
		user.setEmail(register.getEmail());
//		user.setPassword(passwordEncoder.encode(register.getPassword()));
		user.setPassword(register.getPassword());
		user.setName(register.getName());
		user.setLastname(register.getLastname());
		user.setUsername(register.getUsername());
		user.setRol(Rol.NORMAL);
		return user;
	}

}
