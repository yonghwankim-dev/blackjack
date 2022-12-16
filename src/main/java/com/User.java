package com;

import java.util.List;

public abstract class User implements Comparable<User> {
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

    public int getScore() {
        return hands.stream()
                    .map(Card::getValue)
                    .mapToInt(CardValue::getValue)
                    .sum();
    }

    public int getOpenedScore(){
        return hands.stream()
                    .filter(Card::isOpened)
                    .map(Card::getValue)
                    .mapToInt(CardValue::getValue)
                    .sum();
    }

    @Override
    public int compareTo(User user) {
        return Integer.compare(getScore(), user.getScore());
    }

    public void resetHands(){
        hands.clear();
    }
}
