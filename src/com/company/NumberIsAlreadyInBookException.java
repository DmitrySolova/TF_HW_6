package com.company;

public class NumberIsAlreadyInBookException extends Exception {

    public NumberIsAlreadyInBookException(String message) {
        super(message);
    }
}