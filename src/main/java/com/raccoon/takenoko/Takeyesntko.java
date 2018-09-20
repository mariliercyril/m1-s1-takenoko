package com.raccoon.takenoko;

import com.raccoon.takenoko.Game.Game;

public class Takeyesntko {
    public static boolean VERBOSE = true;

    public static void main(String[] args) {

        Game game = new Game();

        System.out.println("Angry Raccoons presents");
        System.out.println("TAKENOKO");
        game.start();

    }


    /**
     * Allows a conditionnal print
     * @param str The String to be printed.
     */
    public static void print(String str) {
        if (VERBOSE) {
            System.out.println(str);
        }
    }

}
