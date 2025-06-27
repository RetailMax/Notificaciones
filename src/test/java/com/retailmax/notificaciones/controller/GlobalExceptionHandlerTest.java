package com.retailmax.notifications.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private WebRequest webRequest;
    private MockHttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockRequest = new MockHttpServletRequest();
        webRequest = new ServletWebRequest(mockRequest);
    }

    @Test
    @DisplayName("Debería manejar EntityNotFoundException correctamente")
    void testHandleEntityNotFoundException() {
        // Given
        jakarta.persistence.EntityNotFoundException exception = 
            new jakarta.persistence.EntityNotFoundException("Usuario no encontrado");

        // When
        ResponseEntity<Object> response = globalExceptionHandler.handleEntityNotFoundException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Usuario no encontrado"));
    }

    @Test
    @DisplayName("Debería manejar DuplicateKeyException correctamente")
    void testHandleDuplicateKeyException() {
        // Given
        org.springframework.dao.DuplicateKeyException exception = 
            new org.springframework.dao.DuplicateKeyException("Ya existe un usuario con ese email");

        // When
        ResponseEntity<Object> response = globalExceptionHandler.handleDuplicateKeyException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Ya existe un usuario con ese email"));
    }

    @Test
    @DisplayName("Debería manejar MethodArgumentNotValidException correctamente")
    void testHandleMethodArgumentNotValidException() {
        // Given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);

        // When
        ResponseEntity<Object> response = globalExceptionHandler.handleMethodArgumentNotValidException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Validation failed"));
    }

    @Test
    @DisplayName("Debería manejar ConstraintViolationException correctamente")
    void testHandleConstraintViolationException() {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolationException exception = new ConstraintViolationException("Validación fallida", violations);
        ResponseEntity<Object> response = globalExceptionHandler.handleConstraintViolationException(exception, webRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Validación fallida"));
    }

    @Test
    @DisplayName("Debería manejar excepciones genéricas correctamente")
    void testHandleGenericException() {
        Exception exception = new Exception("Error genérico");
        ResponseEntity<Object> response = globalExceptionHandler.handleGenericException(exception, webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Error genérico"));
    }

    @Test
    @DisplayName("Debería manejar IllegalArgumentException correctamente")
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Argumento inválido");
        ResponseEntity<Object> response = globalExceptionHandler.handleIllegalArgumentException(exception, webRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Argumento inválido"));
    }
}