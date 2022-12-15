package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final int CARD_DECK_NUMBER = 4;

    private final User player;
    private final User dealer;
    private final GameInput gameInput;
    private final OutputView view;
    private final List<Card> cards;
    public Game(User player, User dealer) {
        this.player = player;
        this.dealer = dealer;
        this.gameInput = new GameInput();
        this.view = new OutputView();
        this.cards = createCardDeck(CARD_DECK_NUMBER);

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

    public List<Card> createCardDeck(int n){
        List<Card> result = new ArrayList<>();
        Shape[] shapes = {Shape.HEART, Shape.DIAMOND, Shape.CLOVER, Shape.SPADE};
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

    public void showPlayerRemainingPoint() {
        view.showPlayerRemainingPoint(player);
    }

    public void showHandDealingToPlayer() {
        view.showHandDealingToPlayer();
    }

    public void showPlayerChoseResult(int chose) {
        String result = chose == 1 ? "히트" : "스탠드";
        view.showPlayerChoseResult(player, result);
    }

    public void dealingCardAll(int cardNum){
        for(int i = 0; i < cardNum; i++){
            dealingCard(player);
            dealingCard(dealer);
        }
    }

    private void dealingCard(User user){
        Random random = new Random();
        int randomIdx = random.ints(0, cards.size()).findFirst().getAsInt();
        Card randomCard = cards.remove(randomIdx);
        user.addCard(randomCard);
    }

    public void dealingCardOneToUser(User user){
        dealingCard(user);
    }

    public void showHands() {
        view.showHands(player, dealer);
    }

    public void compareScore() {
        if(dealer.compareTo(player) > 0){ // 딜러 승
            view.showWinner(dealer);
        }
        if(dealer.compareTo(player) < 0){ // 플레이어 승
            view.showWinner(player);
        }
        if(dealer.compareTo(player) == 0){ // 무승부
            view.showDraw();
        }
    }

    public String inputContinueGameChose() {
        return gameInput.inputContinueGameChose(dealer);
    }

    public void givePointToWinner(int point) {
        Player winner = (Player) player;
        winner.addPoint(point * 2);
    }

    public boolean isBurst(User user) {
        return user.getScore() > 21;
    }
}
