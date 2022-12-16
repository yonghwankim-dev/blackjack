package com;

import java.util.Arrays;

public enum Chose {
    HIT("히트", 1), STAND("스탠드", 2);

    private String name;
    private int number;

    Chose(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public static Chose toChose(int number){
        return Arrays.stream(Chose.values()).filter(c->c.number == number).findFirst().get();
    }

    public boolean isHIT(){
        return name.equals("히트");
    }
}
