package com;

import java.util.regex.Pattern;

public class GameInputValidate {
    private static final Pattern PLAYER_NAME = Pattern.compile("[A-Z]{3}"); // 대문자 영어 3글자

    public static boolean validatePlayerName(String input){
        return PLAYER_NAME.matcher(input).matches();
    }
}
