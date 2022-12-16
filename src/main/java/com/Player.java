package com;

import java.util.ArrayList;

public class Player extends User{
    private int point;
    private PlayerInput playerInput;
    public Player(){
        this("");
    }

    public Player(String name){
        this(name, 0);
    }

    public Player(String name, int point) {
        super(name, new ArrayList<>());
        this.point = point;
        this.playerInput = new PlayerInput();
    }

    public void hit(Dealer dealer){
        addCard(dealer.dealingCard());
    }

    public int getPoint() {
        return point;
    }

    public void addPoint(int point){
        this.point += point;
    }

    public String inputName() {
        return playerInput.inputName();
    }

    public Chose inputChose() {
        return Chose.toChose(playerInput.inputChose(this));
    }

    public int inputInitialPoint() {
        return playerInput.inputInitialPoint(getName());
    }

    public int inputBatting() {
        return playerInput.inputBatting(this);
    }

    public String inputContinueGame(Dealer dealer) {
        return playerInput.inputContinueGame(dealer);
    }
}
