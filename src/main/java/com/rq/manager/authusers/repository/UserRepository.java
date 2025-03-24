package com.rq.manager.authusers.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rq.manager.authusers.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>{

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
	
	@Query(value = "SELECT * FROM USER WHERE email = :email"
			+ "AND password = :password" , nativeQuery = true)
	Optional<User> findUser (@Param("email") String email,
			@Param("password") String password);
}
