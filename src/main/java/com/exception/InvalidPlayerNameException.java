package com.exception;

public class InvalidPlayerNameException extends RuntimeException{
    public InvalidPlayerNameException() {
        super("적절하지 않은 이름입니다. ex) KYH");
    }
}
