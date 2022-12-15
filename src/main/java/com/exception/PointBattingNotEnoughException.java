package com.exception;

import com.User;

public class PointBattingNotEnoughException extends RuntimeException{

    public PointBattingNotEnoughException(User player){
        super(String.format("베팅할 포인트가 부족합니다. 현재 포인트 : %d", player.getPoint()));
    }
}
