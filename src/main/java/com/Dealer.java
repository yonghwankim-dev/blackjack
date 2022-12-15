package com;

import java.util.ArrayList;

public class Dealer extends User{
    public Dealer() {
        super(new ArrayList<>());
    }

    public void hit(){

    }

    public void stand(){

    }

    public void dealing(){

    }

    @Override
    public String getName() {
        return "딜러";
    }

    @Override
    public int getPoint() {
        return 0;
    }
}
