package com.example.nutritionapp.exception.impl;

public class InstanceExistedException extends RuntimeException {

    public InstanceExistedException(String errorMessage) {
        super(errorMessage);
    }
}
