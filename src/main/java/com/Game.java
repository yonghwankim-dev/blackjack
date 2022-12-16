package com;

import static com.OutputView.*;

public class Game {
    private static final int CARD_DECK_NUMBER = 4;
    private static final int DEALER_WIN = 1;
    private static final int PLAYER_WIN = -1;

    private final Player player;
    private final Dealer dealer;

    public Game(User player, User dealer) {
        this.player = (Player) player;
        this.dealer = (Dealer) dealer;
    }

    public void start(){
        showStartGame();
        startGame();
        showGameEnd(player);
    }

    public void startGame(){
        String yn = "Y";
        int compareScoreResult;
        while(yn.equals("Y")){
            // 게임 라운드 초기화 및 배팅 금액 입력
            resetCard();
            showRemainingPoint(player);
            int batting = player.inputBatting();
            dealer.dealingTwoCard(player);

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
            yn = player.inputContinueGame(dealer);
        }
    }

    private int startDealerTurn() {
        while(dealer.isNeedHit()){
            dealer.hit(CardStatus.CLOSE);
        }
        return isBurst(dealer) ? PLAYER_WIN : DEALER_WIN;
    }

    private int startPlayerTurn() {
        Chose chose = Chose.HIT;
        while(chose.isHIT()){
            showHands();
            chose = player.inputChose();
            dealer.alertChose(player, chose);

            if(chose.isHIT()){
                player.hit();
            }
            if(isBurst(player)){
                return 1;
            }
        }
        return compareScore();
    }

    private void resetCard() {
        player.resetHands();
        dealer.resetHands();
    }

    private void calculateCompareScoreResult(int result, int point) {
        if(result > 0){
            player.addPoint(point * -1);
        }
        if(result < 0){
            player.addPoint(point * 2);
        }
    }



    public void addPoint(Player player){
        if(player == null){
            return;
        }
        int point = player.inputInitialPoint();
        player.addPoint(point);
    }

    public void showUser(User user) {
        OutputView.showUser(user);
    }

    public void showStartGame(){
        OutputView.showStartGame();
    }

    public void showHands() {
        OutputView.showHands(player, dealer);
    }

    public void showCompareScoreResult(int result){
        if(result > 0){
            showWinner(dealer);
        }
        if(result < 0){
            showWinner(player);
        }
        if(result == 0){
            showDraw();
        }
    }

    public void showGameEnd(User user){
        showPoint(user);
        OutputView.showGameEnd();
    }

    public int compareScore() {
        return dealer.compareTo(player);
    }

    public void givePointToWinner(int point) {
        player.addPoint(point * 2);
    }

    public boolean isBurst(User user) {
        return user.getScore() > 21;
    }
}
