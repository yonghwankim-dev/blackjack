package com;

public enum Shape {
    HEART("HEART", "♡"),
    DIAMOND("DIAMOND", "◇"),
    CLOVER("CLOVER", "♧"),
    SPADE("SPADE", "♤");

    private final String name;
    private final String value;

    Shape(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }
}
