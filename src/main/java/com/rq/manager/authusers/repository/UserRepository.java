package com.rq.manager.authusers.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rq.manager.authusers.entity.User;

/**
 * The Interface UserRepository.
 */
public interface UserRepository extends JpaRepository<User, UUID>{

	/**
	 * Exists by email.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	boolean existsByEmail(String email);

	/**
	 * Exists by username.
	 *
	 * @param username the username
	 * @return true, if successful
	 */
	boolean existsByUsername(String username);
	
	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the optional the user
	 */
	Optional<User> findByUsername(String username);
	
	/**
	 * Find by identifier.
	 *
	 * @param identifier the identifier
	 * @return the optional user
	 */
	@Query(value = "SELECT * FROM user u WHERE u.username = :identifier OR "
			+ "u.email = :identifier OR u.num_phone = :identifier",
			nativeQuery = true)
	Optional<User> findByIdentifier(@Param("identifier") String identifier);
}
