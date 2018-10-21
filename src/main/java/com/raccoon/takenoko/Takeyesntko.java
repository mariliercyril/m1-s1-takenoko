package com.raccoon.takenoko;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.player.Player;

import org.apache.log4j.Logger;

public class Takeyesntko {

    static final Logger LOGGER = Logger.getLogger(Takeyesntko.class);

    private static boolean verbose = true;

    public static void main(String[] args) {

        print(" ________      _       __   __   ______    __    __   ________   __   __   ________  ");
        print("|__    __|   /   \\    |  | /  / |   ___|  |  \\  |  | |   __   | |  | /  / |   __   | ");
        print("   |  |     / /_\\ \\   |  |/  /  |  |__    |  |\\ |  | |  |  |  | |  |/  /  |  |  |  | ");
        print("   |  |    / _____ \\  |     <   |   __|_  |  | \\|  | |  |__|  | |     <   |  |__|  | ");
        print("   |__|   /_/     \\_\\ |__|\\__\\  |_______| |__|  \\__| |________| |__|\\__\\  |________| ");
        print("                                                         Presented by angry raccoons\n");

        /*
        Arguments parsing
         */
        if (args.length <= 0) {
            launch1000gamesNoJutsu();
        } else {
            launch1gameNoJutsu();
        }
    }

    /**
     * Allows a conditionnal print
     *
     * @param str The String to be printed.
     */
    public static void print(String str) {
        if (verbose) {
            LOGGER.info(str);
        }
    }

    /**
     * Launches the game, verbose mode
     */
    public static void launch1gameNoJutsu() {

        Game game = new Game();
        game.start();
    }

    /**
     * Launches 1000 games and prints out the output
     */
    public static int launch1000gamesNoJutsu() {
        verbose = false;
        float nbGames = 1000;
        int nbPlayers = 4;
        int[] wins = new int[nbPlayers];
        int[] scores = new int[nbPlayers];
        int voidedGames = 0;
        String[] playersTypes = new String[nbPlayers];

        for (int i = 0; i < nbGames; i++) {
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
        verbose = true;
        print(String.format(" -- Launched %6.0f games!", nbGames));
        print(String.format("| %-8s| %-14s| %-12s| %-9s|", "Player ", "Type","Victories", "Score"));
        for (int i = 0; i < wins.length; i++) {
            print(String.format("| #%-7d|  %-13s|     %5.1f %% |%9d |", ( i + 1 ), playersTypes[i], (float)wins[i]*100 / (nbGames), scores[i]));
        }
        print(String.format(" -- There has been %d void games where all players' scores were 0 (roughly %3.1f percents)", voidedGames, (voidedGames * 100 / nbGames)));

        // Checksum : if the checksum is not nbGames, points were badly distributed
        int totalGames = 0;
        for (int w : wins) {
            totalGames += w;
        }
        print(String.format(" -- Checksum : won + voided games adds up to %d (should be %3.0f)%n", totalGames + voidedGames, nbGames));
        return totalGames + voidedGames;
    }

    /**
     * Allows to inject (i.e. at runtime) a verbose value (which is 'true' by default).
     *
     * @param verbose typically a new verbose value
     */
    public static void setVerbose(boolean verbose) {

        Takeyesntko.verbose = verbose;
    }

}