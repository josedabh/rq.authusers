package com.rq.manager.authusers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rq.manager.authusers.bean.Key;
import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.admin.UserResponse;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.jwt.JwtService;
import com.rq.manager.authusers.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	
	@InjectMocks
	private AuthService authService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private JwtService jwtService;
	
	@Mock 
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	
	// Mockear otros servicios o repositorios necesarios para las pruebas
	
//	@Test
    void registerUserSuccessfully() {
        Register register = new Register();
        register.setEmail("test@test.com");
        register.setUsername("testuser");
        register.setPassword("password123");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(jwtService.generarToken(any())).thenReturn("token123");

        Authentication auth = mock(Authentication.class);
        AuthenticationManager authManager = mock(AuthenticationManager.class);
        when(authenticationManagerBuilder.getObject()).thenReturn(authManager);
        when(authManager.authenticate(any())).thenReturn(auth);

        Key result = authService.registerUser(register);

        assertNotNull(result);
        assertEquals("token123", result.getToken());
    }

    @Test
    void registerUserWithExistingEmailThrowsException() {
        Register register = new Register();
        register.setEmail("existing@test.com");

        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        assertThrows(CustomException.class, () -> authService.registerUser(register));
    }

    @Test
    void authenticateUserSuccessfully() {
        Login login = new Login();
        login.setIdentifier("testuser");
        login.setPassword("password123");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encoded_password");

        when(userRepository.findByIdentifier("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(true);
        when(jwtService.generarToken(any())).thenReturn("token123");

        Authentication auth = mock(Authentication.class);
        AuthenticationManager authManager = mock(AuthenticationManager.class);
        when(authenticationManagerBuilder.getObject()).thenReturn(authManager);
        when(authManager.authenticate(any())).thenReturn(auth);

        Key result = authService.authenticateUser(login);

        assertNotNull(result);
        assertEquals("token123", result.getToken());
    }

    @Test
    void authenticateUserWithInvalidCredentialsThrowsException() {
        Login login = new Login();
        login.setIdentifier("testuser");
        login.setPassword("wrongpass");

        User user = new User();
        user.setPassword("encoded_password");

        when(userRepository.findByIdentifier("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encoded_password")).thenReturn(false);

        assertThrows(CustomException.class, () -> authService.authenticateUser(login));
    }

//    @Test
    void getUserSuccessfully() {
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("testuser");

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@test.com");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserResponse result = authService.getUser();

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void getUserWithInvalidUserThrowsException() {
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("nonexistent");
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> authService.getUser());
    }

    @Test
    void logoutClearsSecurityContext() {
        authService.logout();
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
	
}
