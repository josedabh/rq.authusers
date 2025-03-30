package com.rq.manager.authusers.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.Rol;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.mapper.AdminMapper;
import com.rq.manager.authusers.mapper.UserMapper;
import com.rq.manager.authusers.repository.ChallengeRepository;
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
	
	/** The challenge repository. */
	private ChallengeRepository challengeRepository;

	/**
	 * Creates the admin.
	 *
	 * @param register the register
	 * @return the user
	 */
	// Este metodo se va modificar para solo los admins cree otros admins
	public User createAdmin(Register register) {
		if (userRepository.existsByEmail(register.getEmail()) ||
				userRepository.existsByUsername(register.getUsername())) {
			throw new IllegalArgumentException();
		}
		User user = UserMapper.mapRegisterEntity(register, Rol.ADMIN);
		userRepository.save(user);
		return user;
	}

	/**
	 * Gets the list users.
	 *
	 * @return the list users
	 */
	public List<UserResponse> getListUsers() {
		return userRepository.findAll().stream()
				.map(u -> UserMapper.mapEntityUserResponse(u))
				.toList();
	}
	
	/**
	 * Creates the challenge.
	 *
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
	public ChallengeResponse createChallenge(ChallengeRequest challengeRequest) {
		//Averiguar como hacer lo de localDateTime
		Challenge challenge = AdminMapper.mapChallengeRToEntity(challengeRequest);
		challengeRepository.save(challenge);
		return AdminMapper.mapChallengeEntityToResponse(challenge);
	}
	
	/**
	 * List all challenges.
	 *
	 * @return the list challenges
	 */
	public List<ChallengeResponse> listChallenges() {
		return challengeRepository.findAll().stream().map(challenge ->
				AdminMapper.mapChallengeEntityToResponse(challenge))
				.collect(Collectors.toList());
	}
	
	public ChallengeResponse updateChallenge() {
		return null;
	}
	
	/**
	 * Delete challenge.
	 *
	 * @param id 
	 * 			the id challenge
	 */
	public void deleteChallenge(int id) {
		challengeRepository.deleteById(id);
	}
}
