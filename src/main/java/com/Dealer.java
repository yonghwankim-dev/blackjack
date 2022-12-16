package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dealer extends User{
    public Dealer() {
        super("DEALER", new ArrayList<>());
    }

    public void hit(Game game){
        hit(game, CardStatus.OPEN);
    }

    public void hit(List<Card> cards, CardStatus status){
        dealingCard(this, status, cards);
    }

    public void stand(){

    }

    public void dealing(){

    }

    public void dealingCardAll(int cardNum, Game game, Player player){
        for(int i = 0; i < cardNum; i++){
            player.hit(game);
            this.hit(game);
        }
        closeAllCardExceptOneCard();
    }

    private void dealingCard(User user, CardStatus status, List<Card> cards) {
        Random random = new Random();
        int randomIdx = random.ints(0, cards.size()).findFirst().getAsInt();
        Card randomCard = cards.remove(randomIdx);
        randomCard.setStatus(status);
        user.addCard(randomCard);
    }


    public boolean isNeedHit(){
        return getScore() <= 16;
    }

    @Override
    public int getPoint() {
        return 0;
    }

    public void closeAllCardExceptOneCard() {
        List<Card> hands = getHands();
        // 모든 카드 비공개 상태로 설정
        hands.stream().forEach(Card::toClose);

        // 첫번째 카드만 오픈 상태로 설정
        hands.get(0).toOpen();
    }

    public void openAllCard() {
        getHands().stream().forEach(Card::toOpen);
    }
}
