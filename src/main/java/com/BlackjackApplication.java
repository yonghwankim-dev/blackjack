package com;

public class BlackjackApplication {

    public static void main(String[] args) {
        OutputView view = new OutputView();
        PlayerInput playerInput = new PlayerInput();
        String name = playerInput.inputName();
        int point = playerInput.inputInitialPoint(name);
        Player player = new Player(name, point);
        view.showUser(player);
        new Game(player, new Dealer()).start();
    }

}
