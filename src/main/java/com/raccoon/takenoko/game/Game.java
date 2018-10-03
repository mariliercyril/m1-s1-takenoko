package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.objective.AbstractObjective;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.parcel.AlignmentParcelObjective;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.player.RandomBot;
import com.raccoon.takenoko.tool.ForbiddenActionException;

import java.util.*;
import java.util.List;

/**
 * Class representing the games, and allowing to interract with it
 */
public class Game {

    private List<Player> players;           // The Players participating the game
    private LinkedList<Tile> tilesDeck;     // The deck in which players get the tiles
    private Board board;                    // The game board, with all the tiles
    private Gardener gardener;              // The gardener (obviously)
    private Stack<AbstractObjective> objectivesDeck; // The deck of objective cards. Not used yet.
    private Panda panda;                    // Probably the panda
    private List<AbstractObjective> patternObjectives;

    public Game() {                 // Default constructor: 1v1 game

        this.gardener = new Gardener();
        this.panda = new Panda();
        int numberOfPlayers = 2;
        this.players = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            Player newPlayer = new RandomBot();
            players.add(newPlayer);
        }

        patternObjectives = new ArrayList<>();

        board = new HashBoard(new BasicTile());     //  The pond tile is placed first
        initTileDeck();
        initObjectiveDeck();
    }

    public Game(List<Player> players) {
        this.gardener = new Gardener();
        this.panda = new Panda();
        this.players = players;
        board = new HashBoard(new BasicTile());
        initTileDeck();
        initObjectiveDeck();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public boolean gameOver() {     // Currently, the game is over as soon as a player reaches a score of 9 or the tilesDeck is empty
        for (Player p : players) {
            if (p.getScore() >= 9 || tilesDeck.isEmpty()) return true;
        }

        return false;
    }

    public void start() {           // Starts the game: while the game isn't over, each player plays
        int i = 0;
        while (!gameOver()) {
            Takeyesntko.print("\nPlayer #" + players.get(i).getId() + " is playing now.");
            try {
                players.get(i).play(this);
            } catch (ForbiddenActionException e){
                Takeyesntko.print("\nPlayer #" + players.get(i).getId() + " tried to cheat: " + e.getMessage() + " I can see you, Player #" +  players.get(i).getId() + "!");
            }
            i = (i + 1) % players.size();   // To keep i between 0 and the size of the list of players
        }
        printRanking();
    }

    public Tile getTile() {         //  Takes a tile from the tilesDeck
        return tilesDeck.poll();
    }

    public ArrayList<Tile> getTiles() {       // Takes n (three) tiles from the tilesDeck

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
                tilesDeck.push(new BasicTile(c));
            }
        }
        Collections.shuffle(tilesDeck);
    }

    private void initObjectiveDeck() {

        objectivesDeck = new Stack<>();

        for (int i = 0; i < 30; i++) {
        	for (Color color : Color.values()) {
        		objectivesDeck.push(new AlignmentParcelObjective(color));
        	}
        }
    }

    private void printRanking() {
        players.sort((Player p1, Player p2) -> p2.getScore() - p1.getScore());
        Takeyesntko.print("\n RANKING");
        for (Player pl : players) {
            Takeyesntko.print("Player #" + pl.getId() + " has " + pl.getScore() + " points.");
            Takeyesntko.print("Player #" + pl.getId() + " stomach : " + pl.getStomach());
        }
    }

    protected List getTilesDeck() {
        return tilesDeck;
    }

    public Gardener getGardener() {
        return gardener;
    }

    /**
     * Allow a player to put down a tile on the board. It also check for the completion of the
     * objectives that might be changed by this action.
     * @param tile The tile to put down, with its position attribute set
     */
    public void putDownTile(Tile tile) {
        this.board.set(tile.getPosition(), tile);
        for (AbstractObjective objective : this.patternObjectives) {
            objective.checkIfCompleted(tile, this.board);
        }
    }

    /**
     * Allows a player to draw an objective card
     * @return the first objective card of the deck
     */
    public Objective drawObjective() {

        AbstractObjective objective = objectivesDeck.pop();

        /*
        We add the drawn objective to the adequate list of objective, to maintain its completion.
        Here, we just have one type of objective, which is not even drawn but created on demand,
        so non need to check its type we can jsut add it to the pattern list
         */
        this.patternObjectives.add(objective);

        return objective;
    }

    public Panda getPanda() {
        return panda;
    }

    public void purge(){
        board = new HashBoard(new BasicTile());
        initTileDeck();
        Player.reinitCounter();
    }
}