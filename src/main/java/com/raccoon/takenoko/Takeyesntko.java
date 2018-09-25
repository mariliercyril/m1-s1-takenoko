package com.raccoon.takenoko;

import com.raccoon.takenoko.game.Game;

public class Takeyesntko {
    public static boolean VERBOSE = true;

    public static void main(String[] args) {

        Game game = new Game();
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


//  ________      _       __   __   ______    __    __   ________   __   __   ________  
// |__    __|   /   \\    |  | /  / |   ___|  |  \\  |  | |   __   | |  | /  / |   __   | 
//    |  |     / /_\\ \\   |  |/  /  |  |__    |  |\\ |  | |  |  |  | |  |/  /  |  |  |  | 
//    |  |    / _____ \\  |     <   |   __|_  |  | \\|  | |  |__|  | |     <   |  |__|  | 
//    |__|   /_/     \\_\\ |__|\\__\\  |_______| |__|  \\__| |________| |__|\\__\\  |________| 