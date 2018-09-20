package com.raccoon.takenoko;

import com.raccoon.takenoko.Game.Game;

public class Takeyesntko {
    private static final boolean VERBOSE = true;

    public static void main(String[] args) {

        Game game = new Game();
        game.start();

        System.out.println("Takenoko");

    }

    public static void print(String str) {
        if (VERBOSE) {
            System.out.println(str);
        }
    }

}
