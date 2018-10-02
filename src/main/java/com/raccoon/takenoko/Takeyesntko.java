package com.raccoon.takenoko;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.player.Player;

public class Takeyesntko {
    public static boolean VERBOSE = true;

    public static void main(String[] args) {

        // comment either one or the other instruction
        launch1gameNoJutsu();
        // launch1000gamesNoJutsu();
    }

    /**
     * Allows a conditionnal print
     *
     * @param str The String to be printed.
     */
    public static void print(String str) {
        if (VERBOSE) {
            System.out.println(str);
        }
    }

    /**
     * Launches the game, verbose mode
     */
    private static void launch1gameNoJutsu() {
        Takeyesntko.VERBOSE = true;
        Game game = new Game();
        game.start();
    }

    /**
     * Launches 1000 games and prints out the output
     */
    private static void launch1000gamesNoJutsu() {
        VERBOSE = false;
        int nbPlayers = 4;
        int[] wins = new int[nbPlayers];
        int[] scores = new int[nbPlayers];
        int voidedGames = 0;
        String playersTypes[] = new String[nbPlayers];

        for (int i = 0; i < 1000; i++) {
            Game game = new Game();
            game.start();

            // First check that it isn't a void game (all players at 0)
            int numberOfNullResults = 0;
            for (Player pl : game.getPlayers()) {
                if (pl.getScore() != 0) {
                    break;
                } else {
                    numberOfNullResults++;
                }
            }
            if (numberOfNullResults == nbPlayers) {
                voidedGames++;
                game.purge();
                continue;
            }

            // increments the wins of the winner
            wins[game.getWinner().getId() - 1] += 1;

            // counts the points
            for (Player pl : game.getPlayers()) {
                scores[pl.getId() - 1] += pl.getScore();
                playersTypes[pl.getId() - 1] = pl.getClass().getSimpleName();
            }
            game.purge();
        }


        // printing out results
        System.out.println(" -- Launched 1000 games!");
        System.out.println("|\tPlayer\t|\t\tType\t|\tVictories\t|\tPoints\t|");
        for (int i = 0; i < wins.length; i++) {
            // System.out.println(String.format("Player %d won %d times and accumulated %d points total.", ( i + 1 ), wins[i], scores[i]));
            System.out.println(String.format("|\t\t#%d\t|\t%s\t|\t\t\t%d\t|\t\t%d\t|", ( i + 1 ), playersTypes[i], wins[i], scores[i]));
        }
        System.out.println(String.format(" -- There has been %d void games where all players' scores were 0", voidedGames));

        // Checksum : if the checksum is not 1000, points were badly distributed
        /*
        int totalGames = 0;
        for (int w : wins) {
            totalGames += w;
        }
        System.out.println(String.format(" -- Checksum : won + voided games adds up to %d (should be 1000)\n", totalGames + voidedGames));
        */
    }

}


//  ________      _       __   __   ______    __    __   ________   __   __   ________  
// |__    __|   /   \\    |  | /  / |   ___|  |  \\  |  | |   __   | |  | /  / |   __   | 
//    |  |     / /_\\ \\   |  |/  /  |  |__    |  |\\ |  | |  |  |  | |  |/  /  |  |  |  | 
//    |  |    / _____ \\  |     <   |   __|_  |  | \\|  | |  |__|  | |     <   |  |__|  | 
//    |__|   /_/     \\_\\ |__|\\__\\  |_______| |__|  \\__| |________| |__|\\__\\  |________| 