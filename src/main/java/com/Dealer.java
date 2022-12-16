package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.CardStatus.CLOSE;
import static com.CardStatus.OPEN;
import static com.CardValue.*;

public class Dealer extends User{
    private List<Card> cards;

    public Dealer() {
        super("DEALER", new ArrayList<>());
        this.cards = createCardDeck(4);
    }

    public List<Card> createCardDeck(int n){
        List<Card> result = new ArrayList<>();
        Shape[] shapes = {Shape.HEART, Shape.DIAMOND, Shape.CLOVER, Shape.SPADE};
        CardValue[] values = {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};

        for(int i = 0; i < n; i++){
            int shape_idx = i % shapes.length;
            for(CardValue value : values){
                result.add(new Card(value, shapes[shape_idx], OPEN));
            }
        }

        return result;
    }

    public void hit(){
        hit(CLOSE);
    }

    public void hit(CardStatus status){
        Card drawCard = dealingCard(status);
        addCard(drawCard);
    }

    public boolean isNeedHit(){
        return getScore() <= 16;
    }

    public void closeAllCardExceptOneCard() {
        // 모든 카드 비공개 상태로 설정
        closeAllCard();

        // 첫번째 카드만 오픈 상태로 설정
        openOneCard();
    }

    private void closeAllCard(){
        getHands().forEach(Card::toClose);
    }

    private void openOneCard(){
        getHands().get(0).toOpen();
    }

    public void openAllCard() {
        getHands().forEach(Card::toOpen);
    }

    public void alertDealing() {
        OutputView.alertDealing(getName());
    }

    public void alertChose(Player player, Chose chose) {
        OutputView.alertChose(this, player, chose);
    }

    public void dealingTwoCard(Player player) {
        for(int i = 0; i < 2; i++){
            player.addCard(dealingCard());
            addCard(dealingCard());
        }
        closeAllCardExceptOneCard();
        alertDealing();
    }

    public Card dealingCard() {
        return dealingCard(OPEN);
    }

    public Card dealingCard(CardStatus status) {
        Random random = new Random();
        int idx = random.ints(0, cards.size()).findFirst().getAsInt();
        Card card = cards.remove(idx);
        card.setStatus(status);
        return card;
    }

    public void resetCards(){
        cards = createCardDeck(4);
    }
}
