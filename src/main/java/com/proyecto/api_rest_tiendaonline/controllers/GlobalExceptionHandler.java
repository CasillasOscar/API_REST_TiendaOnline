package com.proyecto.api_rest_tiendaonline.controllers;

import com.proyecto.api_rest_tiendaonline.exceptions.CustomException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageReadableException(HttpMessageNotReadableException e){
        Map<String, String> error = new HashMap<>();
        error.put("error", "formato de dato incorrecto.");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e){
        Map<String, String> errores = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errores = new HashMap<>();

        e.getConstraintViolations().forEach(violation -> {
            String campo = violation.getPropertyPath().toString();
            String mensaje = violation.getMessage();
            errores.put(campo, mensaje);
        });

        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Tipo de dato inválido para el parámetro: " + ex.getName());
        error.put("valor", ex.getValue() != null ? ex.getValue().toString() : null);
        error.put("tipoEsperado", ex.getRequiredType().getSimpleName());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException msg){
        Map<String, String> error = new HashMap<>();
        error.put("error", msg.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

}
