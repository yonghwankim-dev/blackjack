package com;

import java.util.List;

public abstract class User {
    private List<Card> hands;

    public User(List<Card> hands) {
        this.hands = hands;
    }

    public void addCard(Card card){
        hands.add(card);
    }

    public List<Card> getHands() {
        return hands;
    }

    public abstract String getName();
    public abstract int getPoint();
}
