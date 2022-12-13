package com;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hands;
    private int point;

    public Player(String name) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.point = 0;
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
