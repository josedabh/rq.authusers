package com.rq.manager.authusers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rq.manager.authusers.bean.Credentials;
import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.jwt.JwtService;
import com.rq.manager.authusers.repository.UserRepository;
import com.rq.manager.authusers.repository.entity.User;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encoded_pass");
        testUser.setRol(RolEnum.NORMAL);
    }

    @Test
    void registerUser_whenEmailAlreadyExists_throwsCustomException() {
        Register register = new Register();
        register.setEmail("existing@example.com");
        register.setUsername("newuser");
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);
        assertThatThrownBy(() -> authService.registerUser(register)).isInstanceOf(CustomException.class);
    }

    @Test
    void registerUser_whenUsernameAlreadyExists_throwsCustomException() {
        Register register = new Register();
        register.setEmail("new@example.com");
        register.setUsername("existinguser");
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);
        assertThatThrownBy(() -> authService.registerUser(register)).isInstanceOf(CustomException.class);
    }

    @Test
    void authenticateUser_whenPasswordDoesNotMatch_throwsCustomException() {
        Login login = new Login();
        login.setIdentifier("testuser");
        login.setPassword("wrong_pass");
        when(userRepository.findByIdentifier("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrong_pass", "encoded_pass")).thenReturn(false);
        assertThatThrownBy(() -> authService.authenticateUser(login)).isInstanceOf(CustomException.class);
    }

    @Test
    void authenticateUser_whenUserNotFound_throwsCustomException() {
        Login login = new Login();
        login.setIdentifier("unknown");
        login.setPassword("pass");
        when(userRepository.findByIdentifier("unknown")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authService.authenticateUser(login)).isInstanceOf(CustomException.class);
    }

    @Test
    void registerUser_savesUserAndReturnsCredentials() {
        Register register = new Register();
        register.setEmail("new@example.com");
        register.setUsername("newuser");
        register.setPassword("password123");
        register.setName("New");
        register.setLastname("User");
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(jwtService.generarToken(authentication)).thenReturn("jwt_token");
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.of(testUser));
        Credentials result = authService.registerUser(register);
        assertThat(result).isNotNull();
    }
}
