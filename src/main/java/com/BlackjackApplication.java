package com;

public class BlackjackApplication {

    public static void main(String[] args) {
        GameInput gameInput = new GameInput();
        OutputView view = new OutputView();
        String name = gameInput.inputPlayerName();
        int point = gameInput.inputPlayerPoint(name);
        Player player = new Player(name, point);
        view.showPlayerInputConfirm(player);
        new Game(player, new Dealer()).start();
    }

}
