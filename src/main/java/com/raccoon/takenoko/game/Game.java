package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.BamBot;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.player.RandomBot;
import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.ForbiddenActionException;

import java.util.*;
import java.util.List;

/**
 * Class representing the games, and allowing to interract with it
 */
public class Game {

    private Board board;                    // The game board, with all the tiles
    private List<Player> players;           // The Players participating the game

    private LinkedList<Tile> tilesDeck;     // The deck in which players get the tiles

    private Panda panda;                    // Probably the panda
    private Gardener gardener;              // The gardener (obviously)

    private ObjectivePool objectivePool;    // The pool of objective cards

    /*
     *************************************************
     *                 Constructors
     *************************************************
     */

    /**
     * Constructs a 4 players game
     */
    public Game() {
        this(4);
    }

    /**
     * Construct a game with new players, all with the randomBot implementation
     *
     * @param numberOfPlayers the number of players to add to the game
     */
    public Game(int numberOfPlayers) {

        this.gardener = new Gardener();
        this.panda = new Panda();

        this.players = new ArrayList<>();

        Player.reinitCounter();
        Player newPlayer;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i % 2 == 0) {
                newPlayer = new BamBot();
            } else {
                newPlayer = new RandomBot();
            }
            players.add(newPlayer);
        }

        board = new HashBoard(new Tile());     //  The pond tile is placed first
        initTileDeck();

        objectivePool = new ObjectivePool(this);    // Initialisation of the objective pool
    }

    /**
     * Constructs a game with a given list of players. Useful to test and give a specific composition of bots to the game.
     *
     * @param players the list of {@code Players} to add to the game
     */
    public Game(List<Player> players) {
        this.gardener = new Gardener();
        this.panda = new Panda();
        this.players = players;
        board = new HashBoard(new Tile());
        initTileDeck();
        this.objectivePool = new ObjectivePool(this);
    }

    /*
     *************************************************
     *                 Get/Set
     *************************************************
     */

    protected List getTilesDeck() {
        return tilesDeck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public ObjectivePool getObjectivePool() {
        return objectivePool;
    }

    public Board getBoard() {
        return board;
    }

    public Gardener getGardener() {
        return gardener;
    }

    public Panda getPanda() {
        return panda;
    }

    /*
     *************************************************
     *                 Methods
     *************************************************
     */

    public boolean gameOver() {     // Currently, the game is over as soon as a player reaches a score of 9 or the tilesDeck is empty
        for (Player p : players) {
            if (p.getScore() >= 9 || tilesDeck.isEmpty()) { return true; }
        }

        return false;
    }

    public void start() {           // Starts the game: while the game isn't over, each player plays
        int i = 0;
        int turnNumber = 0;
        while (!gameOver()) {

            if (i == 0) {   // If it's the first player turn, I.E. we are at the beginning of a turn
                Takeyesntko.print("\n######################################################");
                Takeyesntko.print("Beginning of turn number " + ++turnNumber);    // We print the new turn number
            }

            Takeyesntko.print("\nPlayer #" + players.get(i).getId() + " " + players.get(i).getClass().getSimpleName() + " is playing now.");
            try {
                players.get(i).play(this);
            } catch (ForbiddenActionException e) {
                Takeyesntko.print("\nPlayer #" + players.get(i).getId() + " tried to cheat: " + e.getMessage() + " I can see you, Player #" + players.get(i).getId() + "!");
            }
            i = ( i + 1 ) % players.size();   // To keep i between 0 and the size of the list of players
        }
        printRanking();
    }

    public Tile getTile() {         //  Takes a tile from the tilesDeck
        return tilesDeck.poll();
    }

    public List<Tile> getTiles() {       // Takes n (three) tiles from the tilesDeck

        ArrayList<Tile> tiles = new ArrayList<>();
        Tile candidate;
        for (int i = 0; i < Constants.NUMBER_OF_TILES_TO_DRAW; i++) {
            candidate = getTile();
            if (candidate != null) {
                tiles.add(candidate);
            }
        }
        return tiles;
    }

    public void putBackTile(Tile tile) {
        tilesDeck.add(tile);
    }

    public Player getWinner() {
        players.sort((Player p1, Player p2) -> p2.getScore() - p1.getScore());

        return players.get(0);
    }

    // used only by this class
    private void initTileDeck() {
        tilesDeck = new LinkedList<>();
        Color[] colors = new Color[]{Color.PINK, Color.GREEN, Color.YELLOW};

        for (Color c : colors) {
            for (int i = 0; i < c.getQuantite(); i++) {
                tilesDeck.push(new Tile(c));
            }
        }
        Collections.shuffle(tilesDeck);
    }

    private void printRanking() {
        players.sort((Player p1, Player p2) -> p2.getScore() - p1.getScore());
        Takeyesntko.print("\n RANKING");
        for (Player pl : players) {
            Takeyesntko.print("Player #" + pl.getId() + " has " + pl.getScore() + " points (" + pl.getClass().getSimpleName() + ")");
        }
    }

    /**
     * Allows a player to put down a tile on the board. It also notifies the objective pool, so the pattern objectives
     * completion is checked.
     *
     * @param tile The tile to put down, with its position attribute set
     */
    public void putDownTile(Tile tile) {

        this.board.set(tile.getPosition(), tile);   // The tile is put in the right position in the board
        // Notification that a tile has been put, the completion of some objectives could be changed
        this.objectivePool.notifyTilePut(tile);

    }

    /**
     * Allows a player to draw an objective card.
     *
     * @return the first objective card of the deck
     */
    public Objective drawObjective(ObjectiveType t) {
        /*
        This might be replaced by a direct call to the draw method of the objectivePool
        in the classes needing it
         */
        return this.objectivePool.draw(t);   // We just get the objective from the pool
    }

    public void purge() {
        board = new HashBoard(new Tile());
        initTileDeck();
        Player.reinitCounter();
    }
}