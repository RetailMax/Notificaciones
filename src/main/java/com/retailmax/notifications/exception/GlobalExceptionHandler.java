package com.retailmax.notifications.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                errors.toString(),
                request.getDescription(false)
        );

        logger.warn("Error de validación: {}", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                ex.getMessage(),
                request.getDescription(false)
        );

        logger.error("Error interno del servidor: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Argumento inválido",
                ex.getMessage(),
                request.getDescription(false)
        );

        logger.warn("Argumento inválido: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }

        // Getters
        public LocalDateTime getTimestamp() { return timestamp; }
        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
        public String getPath() { return path; }

        // Setters
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        public void setStatus(int status) { this.status = status; }
        public void setError(String error) { this.error = error; }
        public void setMessage(String message) { this.message = message; }
        public void setPath(String path) { this.path = path; }
    }
} 