package com;

import java.util.function.Predicate;

import static com.CardValue.ACE;
import static com.CardValue.ACEONE;
import static com.OutputView.*;

public class Game {
    private static final int DEALER_WIN = 1;
    private static final int PLAYER_WIN = -1;
    private static final int DRAW = 0;

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

            // 딜러와 플레이어 차례대로 수행
            startDealerTurn();
            startPlayerTurn();

            // 딜러와 플레이어 점수 비교
            compareScoreResult = compareScore();

            // 딜러의 모든 카드 오픈
            dealer.openAllCard();

            // 비교 결과
            showHands();
            showCompareScoreResult(compareScoreResult);
            calculateCompareScoreResult(compareScoreResult, batting);

            if(player.getPoint() <= 0){
                break;
            }
            yn = player.inputContinueGame(dealer);
        }
    }

    public void startDealerTurn() {
        while(dealer.isNeedHit()){
            dealer.hit(CardStatus.CLOSE);
        }

        if(isBurstWithAce(dealer)){
            dealer.replaceAceToAce(ACE, ACEONE);
        }else if(isNotBurstWithAceOne(dealer)){
            dealer.replaceAceToAce(ACEONE, ACE);
        }
    }

    private boolean isBurstWithAce(User user) {
        return isBurst(user) && containCard(user, ACE);
    }

    private boolean containCard(User user, CardValue cardValue) {
        return user.getHands().stream()
                   .map(Card::getValue)
                   .anyMatch(Predicate.isEqual(cardValue));
    }

    public void startPlayerTurn() {
        Chose chose = Chose.HIT;
        while(chose.isHIT()){
            showHands();
            chose = player.inputChose();
            dealer.alertChose(player, chose);

            if(chose.isHIT()){
                player.hit(dealer);
            }

            if(isBurstWithAce(player)){
                player.replaceAceToAce(ACE, ACEONE);
            }else if(isNotBurstWithAceOne(player)){
                player.replaceAceToAce(ACEONE, ACE);
            }

            if(isBurst(player)){
                break;
            }
        }
    }

    private boolean isNotBurstWithAceOne(User user) {
        return !isBurst(user) && containCard(user, ACEONE);
    }

    private void resetCard() {
        player.resetHands();
        dealer.resetHands();
        dealer.resetCards();
    }

    private void calculateCompareScoreResult(int result, int point) {
        if(result > 0){
            retrievePointByBatting(point);
        }
        if(result < 0){
            givePointByBatting(point);
        }
    }

    public void showUser(Player player) {
        OutputView.showUser(player);
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

    public void showGameEnd(Player player){
        showPoint(player);
        OutputView.showGameEnd();
    }

    public int compareScore() {
        if(isPush(dealer, player)){
            return DRAW;
        }
        if(isBurst(dealer) && isBurst(player)){
            return DEALER_WIN;
        }
        if(isBurst(dealer) && !isBurst(player)){
            return PLAYER_WIN;
        }
        if(!isBurst(dealer) && isBurst(player)){
            return DEALER_WIN;
        }
        return dealer.compareTo(player);
    }

    public void givePointByBatting(int point) {
        player.addPoint(point * 2);
    }

    public void retrievePointByBatting(int point){
        player.addPoint(point * -1);
    }

    public boolean isBurst(User user) {
        return user.getScore() > 21;
    }

    private boolean isPush(User dealer, User player){
        return isBlackJack(dealer) && isBlackJack(player);
    }

    private boolean isBlackJack(User user){
        return user.getScore() == 21;
    }
}
