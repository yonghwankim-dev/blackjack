package com;

import java.util.regex.Pattern;

public class GameInputValidate {
    private static final Pattern PLAYER_NAME = Pattern.compile("[A-Z]{3}"); // 대문자 영어 3글자
    private static final Pattern PLAYER_CHOSE = Pattern.compile("[1-2]"); // 1~2번 1글자
    private static final Pattern PLAYER_POINT = Pattern.compile("^\\d+$");
    public static boolean validatePlayerName(String input){
        return PLAYER_NAME.matcher(input).matches();
    }

    public static boolean validatePlayerChose(String input) {
        return PLAYER_CHOSE.matcher(input).matches();
    }

    public static boolean validatePlayerPoint(String input) {
        return PLAYER_POINT.matcher(input).matches();
    }
}
