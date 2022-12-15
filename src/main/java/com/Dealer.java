package com;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends User{
    public Dealer() {
        super("DEALER", new ArrayList<>());
    }

    public void hit(){

    }

    public void stand(){

    }

    public void dealing(){

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
}
