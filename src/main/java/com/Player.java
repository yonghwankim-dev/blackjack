package com;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hands;

    public Player(String name) {
        this.name = name;
        this.hands = new ArrayList<>();
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
}
