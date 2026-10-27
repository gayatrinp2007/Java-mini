package com.example.certificates.exception;

import com.example.certificates.dto.ApiMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CertificateNotFoundException.class)
    public ResponseEntity<ApiMessage> handleNotFound(CertificateNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage(exception.getMessage()));
    }

    @ExceptionHandler(DuplicateCertificateException.class)
    public ResponseEntity<ApiMessage> handleDuplicate(DuplicateCertificateException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiMessage(exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiMessage> handleBadRequest(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ApiMessage(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiMessage> handleDatabaseConflict(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiMessage("A certificate with conflicting data already exists."));
    }
}
