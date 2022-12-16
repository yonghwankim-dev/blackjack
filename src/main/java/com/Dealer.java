package com;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends User{
    public Dealer() {
        super("DEALER", new ArrayList<>());
    }

    public void hit(Game game){
        hit(game, CardStatus.OPEN);
    }

    public void hit(Game game, CardStatus status){
        game.dealingCardOneToUser(this, status);
    }

    public void stand(){

    }

    public void dealing(){

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
        hands.stream().forEach(c->c.toClose());

        // 첫번째 카드만 오픈 상태로 설정
        hands.get(0).toOpen();
    }

    public void openAllCard() {
        getHands().stream().forEach(Card::toOpen);
    }

    public void alertDealing() {
        System.out.printf("%s : 패를 나누어줬습니다.%n", getName());
    }

    public void alertChose(User user, Chose chose) {
        System.out.printf("%s : %3s님 %s를 선택하셨습니다.%n", getName(), user.getName(), chose.getName());
    }
}
