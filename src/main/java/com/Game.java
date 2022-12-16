package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final int CARD_DECK_NUMBER = 4;
    private static final int DEALER_WIN = 1;
    private static final int PLAYER_WIN = -1;

    private final Player player;
    private final Dealer dealer;
    private final GameInput gameInput;
    private final OutputView view;
    private List<Card> cards;

    public Game(User player, User dealer) {
        this.player = (Player) player;
        this.dealer = (Dealer) dealer;
        this.gameInput = new GameInput();
        this.view = new OutputView();
        this.cards = createCardDeck(CARD_DECK_NUMBER);
    }

    public void start(){
        String yn = "Y";
        int compareScoreResult;
        showStartGame();
        while(yn.equals("Y")){
            // 게임 라운드 초기화 및 배팅 금액 입력
            int batting = startRound();

            compareScoreResult = startDealerTurn();
            if(compareScoreResult != PLAYER_WIN){
                compareScoreResult = startPlayerTurn();
            }

            // 딜러의 모든 카드 오픈
            dealer.openAllCard();

            // 비교 결과
            showHands();
            showCompareScoreResult(compareScoreResult);
            calculateCompareScoreResult(compareScoreResult, batting);
            yn = inputContinueGameChose();
        }
        view.showGameEnd(player);
    }

    private int startDealerTurn() {
        while(dealer.isNeedHit()){
            dealer.hit(this, CardStatus.CLOSE);
        }
        return isBurst(dealer) ? PLAYER_WIN : DEALER_WIN;
    }

    private int startPlayerTurn() {
        Chose chose = Chose.HIT;
        while(chose.isHIT()){
            showHands();
            chose = inputPlayerChose();
            showPlayerChoseResult(chose);

            if(chose.isHIT()){
                player.hit(this);
            }
            if(isBurst(player)){
                return 1;
            }
        }
        return compareScore();
    }

    private int startRound() {
        resetGame();
        showPlayerRemainingPoint();
        int batting = inputPlayerPointBatting();
        showHandDealingToPlayer();
        dealingCardAll(2);
        return batting;
    }

    private void resetGame() {
        player.resetHands();
        dealer.resetHands();
        cards = createCardDeck(4);
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

    public Chose inputPlayerChose(){
        return Chose.toChose(gameInput.inputPlayerChose(player));
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

    public void showPlayerChoseResult(Chose chose) {
        view.showPlayerChoseResult(player, chose);
    }

    public void showStartGame(){
        view.showStartGame();
    }

    public void showHands() {
        view.showHands(player, dealer);
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

    public void dealingCardAll(int cardNum){
        for(int i = 0; i < cardNum; i++){
            player.hit(this);
            dealer.hit(this);
        }
        dealer.closeAllCardExceptOneCard();
    }

    private void dealingCard(User user){
        dealingCard(user, CardStatus.OPEN);
    }

    private void dealingCard(User user, CardStatus status) {
        Random random = new Random();
        int randomIdx = random.ints(0, cards.size()).findFirst().getAsInt();
        Card randomCard = cards.remove(randomIdx);
        randomCard.setStatus(status);
        user.addCard(randomCard);
    }

    public void dealingCardOneToUser(User user){
        dealingCard(user);
    }

    public void dealingCardOneToUser(User user, CardStatus status){
        dealingCard(user, status);
    }

    public int compareScore() {
        return dealer.compareTo(player);
    }

    public String inputContinueGameChose() {
        return gameInput.inputContinueGameChose(dealer);
    }

    public void givePointToWinner(int point) {
        Player winner = player;
        winner.addPoint(point * 2);
    }

    public boolean isBurst(User user) {
        return user.getScore() > 21;
    }
}
