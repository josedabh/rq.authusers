/*
 * 
 */
package com.rq.manager.authusers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.repository.UserRepository;
import com.rq.manager.authusers.repository.entity.User;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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
    void loadUserByUsername_whenFound_returnsUserDetails() {
        when(userRepository.findByIdentifier("testuser")).thenReturn(Optional.of(testUser));
        UserDetails details = userService.loadUserByUsername("testuser");
        assertThat(details).isNotNull();
        assertThat(details.getUsername()).isEqualTo("testuser");
        assertThat(details.getAuthorities()).isNotEmpty();
    }

    @Test
    void loadUserByUsername_whenNotFound_throwsCustomException() {
        when(userRepository.findByIdentifier("unknown")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.loadUserByUsername("unknown")).isInstanceOf(CustomException.class);
    }

    @Test
    void loadUserByUsername_returnsAuthorityMatchingRole() {
        testUser.setRol(RolEnum.ADMIN);
        when(userRepository.findByIdentifier("admin")).thenReturn(Optional.of(testUser));
        UserDetails details = userService.loadUserByUsername("admin");
        boolean hasAdminAuthority = details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        assertThat(hasAdminAuthority).isTrue();
    }
}
