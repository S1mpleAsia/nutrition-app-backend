package com.example.nutritionapp.exception;

import com.example.nutritionapp.exception.impl.GeneralListException;
import com.example.nutritionapp.exception.impl.InstanceExistedException;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.http.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ExceptionHandler({InstanceExistedException.class, InstanceNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponse<String> handleInstanceExistedException(RuntimeException e) {
        return GeneralResponse.error(e.getMessage(), null);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponse<String> handleRuntimeException(RuntimeException e) {
        return GeneralResponse.error(e.getMessage(), null);
    }

    @ExceptionHandler(GeneralListException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponse<String> handleGeneralListException(RuntimeException e) {
        return GeneralResponse.error(e.getMessage(), null);
    }
}

