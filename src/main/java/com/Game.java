package com;

import java.io.IOException;

public class Game {
    private Player player;
    private Dealer dealer;
    private GameInput gameInput;
    public Game(Player player, Dealer dealer) {
        this.player = player;
        this.dealer = dealer;
        this.gameInput = new GameInput();
    }

    public void start(){

    }

    public String inputPlayerName(){
        String name;
        System.out.println("플레이어의 이름(3 english letters)은?");
        while(true){
            try {
                name = gameInput.input();
                break;
            } catch (IOException e) {
                System.out.println("적절하지 않은 이름입니다. ex) KYH");
            }
        }
        return name;
    }

    public void showHands(){

    }

    public void inputHitOrStand(){

    }

    public void showDealerChose(){

    }

    public void showResult(){

    }
}
