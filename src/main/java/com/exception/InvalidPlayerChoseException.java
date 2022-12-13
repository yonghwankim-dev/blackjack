package com.exception;

public class InvalidPlayerChoseException extends RuntimeException{
    public InvalidPlayerChoseException() {
        super("잘못된 선택입니다. ex) 1");
    }
}
