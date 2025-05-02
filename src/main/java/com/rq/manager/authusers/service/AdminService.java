package com.rq.manager.authusers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.admin.UserResponse;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.mapper.UserMapper;
import com.rq.manager.authusers.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * The Class AdminService.
 */
@Service
@AllArgsConstructor
public class AdminService {

	/** The user repository. */
	private UserRepository userRepository;

	/**
	 * Creates the admin.
	 *
	 * @param register the register
	 * @return the user
	 */
	// Este metodo se va modificar para solo los admins cree otros admins
	public User createAdmin(Register register) {
		if (userRepository.existsByEmail(register.getEmail())
				|| userRepository.existsByUsername(register.getUsername())) {
			throw new IllegalArgumentException();
		}
		User user = UserMapper.mapRegisterEntity(register, RolEnum.ADMIN);
		userRepository.save(user);
		return user;
	}

	/**
	 * Gets the list users.
	 * Cambiar esto para no ver la contraseña y otras informacion
	 * Solo ver nombre, username , rol y los historiales de los retos y recompensas
	 * @return the list users
	 */
	public List<UserResponse> getListUsers() {
		return userRepository.findAll().stream()
				.map(u -> UserMapper.mapEntityToResponse(u))
				.toList();
	}

}
