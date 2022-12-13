package com.exception;

public class PointNumberFormatException extends RuntimeException{
    public PointNumberFormatException() {
        super("포인트는 0보다 큰 숫자여야 합니다. ex) 500");
    }
}
