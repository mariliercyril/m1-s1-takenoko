package com.raccoon.takenoko.game;

import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.player.RandomBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Class representing the games, and allowing to interract with it
 */
public class Game {

    private List<Player> players;   // The Players participating the game
    private Queue<Tile> deck;
    private Board board;            // The game board, with all the tiles

    public Game() {                 // Default constructor: 1v1 game
        this.players = new ArrayList<>();
        for (int i = 0; i < 2; i++) players.add(new RandomBot());
        board = new HashBoard(new BasicTile());
        deck = initDeck();
    }

    private Queue<Tile> initDeck() {

        return null;
    }

    public Game(List<Player> players) {
        this.players = players;
        board = new HashBoard(new BasicTile());
        deck = initDeck();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public boolean gameOver() {     // Currently, the game is over as soon as a player reaches a score of 9
        for (Player p : players) {
            if (p.getScore() >= 9) return true;
        }

        return false;
    }

    public void start() {           // Starts the game: while the game isn't over, each player plays
        int i = 0;
        while (!gameOver()) {
            players.get(i).play(this);
            i = (i + 1) % players.size();   // To keep i between 0 and the size of the list of players
        }
    }

    public Tile[] getTile() {       // For picking tiles
        Tile[] res = new Tile[1];
        res[0] = new BasicTile();
        return res;
    }

    public Player getWinner() {
        for (Player p : players) {
            if (p.getScore() >= 9) {
                return p;
            }
        }
        return null;
    }
}
