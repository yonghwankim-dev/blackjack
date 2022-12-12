package com;

public class Card {
    private String name;
    private String shape;
    private int value;

    public Card(String name, String shape, int value) {
        this.name = name;
        this.shape = shape;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getShape() {
        return shape;
    }

    public int getValue() {
        return value;
    }
}
