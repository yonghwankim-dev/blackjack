package com;

import java.util.ArrayList;

public class Dealer extends User{
    public Dealer() {
        super("딜러", new ArrayList<>());
    }

    public void hit(){

    }

    public void stand(){

    }

    public void dealing(){

    }

    @Override
    public int getPoint() {
        return 0;
    }
}
