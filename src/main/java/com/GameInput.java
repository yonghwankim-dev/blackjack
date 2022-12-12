package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.GameInputValidate.validatePlayerChose;
import static com.GameInputValidate.validatePlayerName;

public class GameInput {
    private final BufferedReader br;

    public GameInput() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String inputPlayerName(){
        String name;
        System.out.println("플레이어의 이름(3 english letters)은? ex) KYH");
        while(true){
            try {
                name = br.readLine();
                if(!validatePlayerName(name)) throw new IOException();
                break;
            } catch (IOException e) {
                System.out.println("적절하지 않은 이름입니다. ex) KYH");
            }
        }
        return name;
    }

    public int inputPlayerChose(Player player) {
        String input;
        while(true){
            System.out.printf("%3s님 선택해 주세요 ex) 1%n", player.getName());
            System.out.println("1. 히트(hit)");
            System.out.println("2. 스탠드(stand)");
            try{
                input = br.readLine();
                if(!validatePlayerChose(input)) throw new IOException();
                break;
            } catch (IOException e) {
                System.out.println("잘못된 선택입니다. ex) 1");
            }
        }
        return toInt(input);
    }

    private int toInt(String text){
        return Integer.parseInt(text);
    }
}
