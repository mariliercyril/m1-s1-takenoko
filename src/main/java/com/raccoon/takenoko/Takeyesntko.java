package com.raccoon.takenoko;

import com.raccoon.takenoko.game.Game;

public class Takeyesntko {
    public static boolean VERBOSE = true;

    public static void main(String[] args) {

        Game game = new Game();

        System.out.println(" ________      _       __   __   ______    __    __   ________   __   __   ________  ");
        System.out.println("|__    __|   /   \\    |  | /  / |   ___|  |  \\  |  | |   __   | |  | /  / |   __   | ");
        System.out.println("   |  |     / /_\\ \\   |  |/  /  |  |__    |  |\\ |  | |  |  |  | |  |/  /  |  |  |  | ");
        System.out.println("   |  |    / _____ \\  |     <   |   __|_  |  | \\|  | |  |__|  | |     <   |  |__|  | ");
        System.out.println("   |__|   /_/     \\_\\ |__|\\__\\  |_______| |__|  \\__| |________| |__|\\__\\  |________| ");
        System.out.println("                                                         Presented by Angry Raccoons");
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