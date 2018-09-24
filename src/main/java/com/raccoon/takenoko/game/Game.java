package com.raccoon.takenoko.game;

import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.player.RandomBot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the games, and allowing to interract with it
 */
public class Game {

    private List<Player> players;   // The Players participating the game
    private ArrayDeque<Tile> deck;  // The deck in which players get the tiles
    private Board board;            // The game board, with all the tiles

    public Game() {                 // Default constructor: 1v1 game
        this.players = new ArrayList<>();
        for (int i = 0; i < 2; i++) players.add(new RandomBot());
        board = new HashBoard(new BasicTile());
        initDeck();
    }

    public Game(List<Player> players) {
        this.players = players;
        board = new HashBoard(new BasicTile());
        initDeck();
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

    public Tile getTile() {         //  Takes a tile from the deck
        return deck.remove();
    }

    public Tile[] getTiles() {       // Takes n (three) tiles from the deck

        int nbrTiles = 3;           //  Number of tiles to choose from
        Tile[] tiles = new Tile[3];
        for (int i = 0; i < nbrTiles; i++) {
            tiles[i] = getTile();
        }
        return tiles;
    }

    public Player getWinner() {
        for (Player p : players) {
            if (p.getScore() >= 9) {
                return p;
            }
        }
        return null;
    }

    // used only by this class
    void initDeck() {
        deck = new ArrayDeque<>();
        Color[] colors = new Color[]{Color.PINK, Color.GREEN, Color.YELLOW};

        for (Color c : colors) {
            for (int i = 0; i < c.getQuantite(); i++) {
                deck.push(new BasicTile(c));
            }
        }
    }
}
