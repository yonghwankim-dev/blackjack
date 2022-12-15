package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final int CARD_DECK_NUMBER = 4;

    private final Player player;
    private final Dealer dealer;
    private final GameInput gameInput;
    private final OutputView view;
    private final List<Card> cards;

    public Game(User player, User dealer) {
        this.player = (Player) player;
        this.dealer = (Dealer) dealer;
        this.gameInput = new GameInput();
        this.view = new OutputView();
        this.cards = createCardDeck(CARD_DECK_NUMBER);

    }

    public void start(){
        showStartGame();
        while(player.getPoint() > 0){
            showPlayerRemainingPoint();
            int batting = inputPlayerPointBatting();
            showHandDealingToPlayer();
            dealingCardAll(2);

            while(true){
                showHands();
                int chose = inputPlayerChose();
                showPlayerChoseResult(chose);
                if(chose == 1){
                    dealingCard(player);
                    continue;
                }
                break;
            }
            int compareScoreResult = compareScore();
            showCompareScoreResult(compareScoreResult);
            calculateCompareScoreResult(compareScoreResult, batting);
            String yn = inputContinueGameChose();
            if(yn.equals("N")){
                break;
            }
        }
        view.showGameEnd(player);
    }

    private void calculateCompareScoreResult(int result, int point) {
        if(result > 0){
            player.addPoint(point * -1);
        }
        if(result < 0){
            player.addPoint(point * 2);
        }
    }

    public String inputPlayerName(){
        return gameInput.inputPlayerName();
    }

    public int inputPlayerChose(){
        return gameInput.inputPlayerChose(player);
    }

    public int inputPlayerPoint(String name) {
        return gameInput.inputPlayerPoint(name);
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
        int point = inputPlayerPoint(player.getName());
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

    public void showStartGame(){
        view.showStartGame();
    }

    public void dealingCardAll(int cardNum){
        for(int i = 0; i < cardNum; i++){
            dealingCard(player);
            dealingCard(dealer);
        }
        Dealer d = (Dealer) dealer;
        d.closeAllCardExceptOneCard();
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

    public int compareScore() {
        return dealer.compareTo(player);
    }

    public void showCompareScoreResult(int result){
        if(result > 0){
            view.showWinner(dealer);
        }
        if(result < 0){
            view.showWinner(player);
        }
        if(result == 0){
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
