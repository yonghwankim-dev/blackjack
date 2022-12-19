package com;

import java.util.Objects;

import static com.CardStatus.CLOSE;
import static com.CardStatus.OPEN;

public class Card {
    private final CardValue value;
    private final Shape shape;
    private CardStatus status;

    public Card(CardValue value, Shape shape, CardStatus status) {
        this.value = value;
        this.shape = shape;
        this.status = status;
    }

    public CardValue getValue() {
        return value;
    }

    public Shape getShape() {
        return shape;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void toOpen() {
        this.status = OPEN;
    }

    public void toClose() {
        this.status = CLOSE;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public boolean isOpened(){
        return this.status == OPEN;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getValue() == card.getValue() && getShape() == card.getShape();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getShape());
    }

    @Override
    public String toString() {
        return status == CLOSE ? String.format("%5s", "?") :
                String.format("%s%3d", shape.getValue(), value.getValue());
    }
}
