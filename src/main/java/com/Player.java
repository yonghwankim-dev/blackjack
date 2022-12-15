package com;

import java.util.ArrayList;

public class Player extends User{
    private final String name;
    private int point;

    public Player(String name){
        this(name, 0);
    }

    public Player(String name, int point) {
        super(new ArrayList<>());
        this.name = name;
        this.point = point;
    }

    public void hit(){

    }

    public void stand(){

    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public void addPoint(int point){
        this.point += point;
    }
}
