package com;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hands;
    private int point;

    public Player(String name){
        this(name, 0);
    }

    public Player(String name, int point) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.point = point;
    }

    public void hit(){

    }

    public void stand(){

    }

    public String getName() {
        return name;
    }

    public List<Card> getHands() {
        return hands;
    }

    public int getPoint() {
        return point;
    }

    public void addPoint(int point){
        this.point += point;
    }
}
