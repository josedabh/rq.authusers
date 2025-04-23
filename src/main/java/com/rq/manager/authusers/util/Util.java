package com.rq.manager.authusers.util;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;

import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
import com.rq.manager.authusers.repository.UserRepository;

/**
 * The Class Util.
 */
public class Util {
	
	private static UserRepository userRepository;
	
	/**
	 * Instantiates a new util.
	 */
	public Util() {
		// Default constructor
	}
	
	/**
	 * Gets the local date time.
	 *
	 * @param date the date
	 * @return the local date time
	 */
	public static LocalDateTime getLocalDateTime(String date) {
		return LocalDateTime.parse(date);
	}
	
	/**
	 * Gets the date.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static String getDate(LocalDateTime date) {
		return date.toString();
	}
	
	/**
	 * Obtiene el usuario a partir del token de seguridad
	 * Se obtiene el nombre de usuario desde el contexto de seguridad
	 * y se busca en la base de datos
	 * Si no se encuentra, lanza una excepción personalizada
	 *
	 * @return the user by token
	 */
	public static User getUserByToken() {
		return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new CustomException(ErrorConstants.NULL_USER));
	}
	
}
