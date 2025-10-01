package com.aryan.us_backend_app.constants;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map> handleValidationException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "400");
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                                .getFieldErrors()
                                .get(0)
                                .getDefaultMessage();

        Map<String, String> response = new HashMap<>();
        response.put("status", "400");
        response.put("message", errorMessage);
        return ResponseEntity.badRequest().body(response);
    }
}
