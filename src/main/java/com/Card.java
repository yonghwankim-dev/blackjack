package com;

import java.util.Objects;

public class Card {
    private final String name;
    private final Shape shape;
    private final int value;
    private CardStatus status;

    public Card(String name, Shape shape, int value) {
        this.name = name;
        this.shape = shape;
        this.value = value;
        this.status = CardStatus.OPEN;
    }

    public String getName() {
        return name;
    }

    public Shape getShape() {
        return shape;
    }

    public int getValue() {
        return value;
    }

    public CardStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getName().equals(card.getName()) && getShape().equals(card.getShape());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getShape());
    }

    @Override
    public String toString() {
        return status == CardStatus.CLOSE ? String.format("%4s", "?") :
                                                     String.format("%s%3d", shape.getValue(), value);
    }

    public void toClose() {
        this.status = CardStatus.CLOSE;
    }

    public void toOpen() {
        this.status = CardStatus.OPEN;
    }

    public boolean isOpened(){
        return this.status == CardStatus.OPEN;
    }
}
