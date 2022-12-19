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

    public void replaceAceToAce(CardValue from, CardValue to){
        for(int i = 0; i < getHands().size(); i++){
            Card card = getHands().get(i);
            if(card.getValue().equals(from)){
                getHands().remove(card);
                getHands().add(new Card(to, card.getShape(), card.getStatus()));
                break;
            }
        }
    }
}
