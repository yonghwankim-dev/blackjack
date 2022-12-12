package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.GameInputValidate.validatePlayerName;

public class GameInput {
    private BufferedReader br;

    public GameInput() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String inputPlayerName(){
        String name = null;
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
}
