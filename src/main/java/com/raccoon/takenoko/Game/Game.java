package com.raccoon.takenoko.Game;

import com.raccoon.takenoko.Player.Player;
import com.raccoon.takenoko.Player.RandomBot;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the games, and allowing to interract with it
 */
public class Game {

    private List<Player> players;   // The Players participating the game
    private Board board;            // The game board, with all the tiles

    public Game() {                 // Default constructor: 1v1 game
        this.players = new ArrayList<>();
        for (int i = 0; i < 2; i++) players.add(new RandomBot());
        //todo : instanciate the board
    }

    public Game(List<Player> players) {
        this.players = players;
        //todo : instanciate the board
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }
    
}
