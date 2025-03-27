package com.rq.manager.authusers.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>{

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByUsernameOrEmailOrNumPhoneAndPassword(String username, String email, String numPhone, String password);

}
