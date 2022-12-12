package com;

public class Game {
    private final Player player;
    private final Dealer dealer;
    private final GameInput gameInput;

    public Game(Player player, Dealer dealer) {
        this.player = player;
        this.dealer = dealer;
        this.gameInput = new GameInput();
    }

    public void start(){

    }

    public String inputPlayerName(){
        return gameInput.inputPlayerName();
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