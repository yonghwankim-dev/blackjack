package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameInput {
    private BufferedReader br;

    public GameInput() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String input() throws IOException {
        return br.readLine();
    }
}
