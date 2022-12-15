package com.exception;

public class InvalidContinueGameChose extends RuntimeException {
    public InvalidContinueGameChose() {
        super("입력이 잘못되었습니다. ex) Y 또는 N");
    }
}
