package com;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private static final int CARD_DECK_NUMBER = 4;

    private final Player player;
    private final Dealer dealer;
    private final GameInput gameInput;
    private final OutputView view;
    private final Set<Card> cardSet;
    public Game(Player player, Dealer dealer) {
        this.player = player;
        this.dealer = dealer;
        this.gameInput = new GameInput();
        this.view = new OutputView();
        this.cardSet = createCardDeck(CARD_DECK_NUMBER);

    }

    public String inputPlayerName(){
        return gameInput.inputPlayerName();
    }

    public int inputPlayerChose(){
        return gameInput.inputPlayerChose(player);
    }

    public int inputPlayerPoint() {
        return gameInput.inputPlayerPoint(player.getName());
    }

    public int inputPlayerPointBatting() {
        return gameInput.inputPlayerPointBatting(player);
    }

    public Set<Card> createCardDeck(int n){
        Set<Card> result = new HashSet<>();
        String[] shapes = {"CLOVER", "HEART", "DIAMOND", "SPADE"};
        String[] names = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        int[] values = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

        for(int i = 0; i < n; i++){
            int shape_idx = i % shapes.length;
            for(int j = 0; j < names.length; j++){
                result.add(new Card(names[j], shapes[shape_idx], values[j]));
            }
        }

        return result;
    }

    public void addPoint(Player player){
        if(player == null){
            return;
        }
        int point = inputPlayerPoint();
        player.addPoint(point);
    }

    public void showPlayerInputConfirm() {
        view.showPlayerInputConfirm(player);
    }
}
