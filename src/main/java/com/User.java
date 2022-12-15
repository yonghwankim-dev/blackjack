package com;

import java.util.List;

public abstract class User {
    private final String name;
    private final List<Card> hands;

    public User(String name, List<Card> hands) {
        this.name = name;
        this.hands = hands;
    }

    public void addCard(Card card){
        hands.add(card);
    }

    public String getName() {
        return name;
    }

    public List<Card> getHands() {
        return hands;
    }

    public abstract int getPoint();

    public int getScore() {
        return hands.stream().mapToInt(Card::getValue).sum();
    }
}
