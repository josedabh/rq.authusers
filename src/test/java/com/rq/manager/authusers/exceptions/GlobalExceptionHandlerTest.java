package com.rq.manager.authusers.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private ErrorMessageService errorMessageService;

    @InjectMocks
    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        when(errorMessageService.getErrorAppName()).thenReturn("users");
        when(errorMessageService.getErrorMessage(anyString(), any(Locale.class))).thenReturn("Error message");
        when(errorMessageService.getErrorDescription(anyString(), any(Locale.class))).thenReturn("Error description");
        when(errorMessageService.getInternalCode(anyString())).thenReturn("1000");
    }

    /**
     * Handle app exception with custom exception returns 400.
     */
    @Test
    void handleAppException_withCustomException_returns400() {
        CustomException ex = new CustomException("some_key");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/test");
        ResponseEntity<ErrorResponse> response = handler.handleAppException(ex, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getPath()).isEqualTo("/api/v1/test");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    void handleAppException_withResourceNotFoundException_returns404() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not_found_key");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/challenge/99");
        ResponseEntity<ErrorResponse> response = handler.handleAppException(ex, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getStatus()).isEqualTo(404);
    }

    @Test
    void handleAppException_withBusinessException_returns422() {
        BusinessException ex = new BusinessException("business_key");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/challenge");
        ResponseEntity<ErrorResponse> response = handler.handleAppException(ex, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody().getStatus()).isEqualTo(422);
    }

    @Test
    void handleAppException_withAcceptLanguageHeader_usesLocaleFromHeader() {
        CustomException ex = new CustomException("some_key");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/test");
        request.addHeader("Accept-Language", "en");
        ResponseEntity<ErrorResponse> response = handler.handleAppException(ex, request);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLocale()).isEqualTo("en");
    }
}
