package com;

public class BlackjackApplication {

    public static void main(String[] args) {
        OutputView view = new OutputView();
        GameInput gameInput = new GameInput();
        String name = gameInput.inputPlayerName();
        int point = gameInput.inputPlayerPoint(name);
        Player player = new Player(name, point);
        view.showUser(player);
        new Game(player, new Dealer()).start();
    }

}
