package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.player.RandomBot;

import java.util.*;

/**
 * Class representing the games, and allowing to interract with it
 */
public class Game {

    private List<Player> players;   // The Players participating the game
    private LinkedList<Tile> deck;  // The deck in which players get the tiles
    private Board board;            // The game board, with all the tiles

    public Game() {                 // Default constructor: 1v1 game
        this.players = new ArrayList<>();
        for (int i = 0; i < 2; i++) players.add(new RandomBot());
        board = new HashBoard(new BasicTile());     //  The pond tile is placed first
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

    public boolean gameOver() {     // Currently, the game is over as soon as a player reaches a score of 9 or the deck is empty
        for (Player p : players) {
            if (p.getScore() >= 9 || deck.isEmpty()) return true;
        }

        return false;
    }

    public void start() {           // Starts the game: while the game isn't over, each player plays
        int i = 0;
        while (!gameOver()) {
            Takeyesntko.print("\nPlayer " + i + " is playing now.");
            players.get(i).play(this);
            i = (i + 1) % players.size();   // To keep i between 0 and the size of the list of players
        }
        printRanking();
    }

    public Tile getTile() {         //  Takes a tile from the deck
        return deck.poll();
    }

    public ArrayList<Tile> getTiles() {       // Takes n (three) tiles from the deck

        int nbrTiles = 3;           //  Number of tiles to choose from
        ArrayList<Tile> tiles = new ArrayList<>();
        Tile candidate;
        for (int i = 0; i < nbrTiles; i++) {
            candidate = getTile();
            if (candidate != null) {
                tiles.add(candidate);
            }
        }
        return tiles;
    }

    public void putBackTile(Tile tile) {
        deck.add(tile);
    }

    public Player getWinner() {
        players.sort((Player p1, Player p2) -> p2.getScore() - p1.getScore());

        return players.get(0);
    }

    // used only by this class
    void initDeck() {
        deck = new LinkedList<>();
        Color[] colors = new Color[]{Color.PINK, Color.GREEN, Color.YELLOW};

        for (Color c : colors) {
            for (int i = 0; i < c.getQuantite(); i++) {
                deck.push(new BasicTile(c));
            }
        }
        Collections.shuffle(deck);
    }

    private void printRanking() {
        players.sort((Player p1, Player p2) -> p2.getScore() - p1.getScore());
        Takeyesntko.print("\n RANKING");
        for (int i = 0; i < players.size(); i++) {
            Takeyesntko.print("Player " + i + " has " + players.get(i).getScore() + " points.");
        }
    }

    protected List getDeck() {
        return deck;
    }
}
