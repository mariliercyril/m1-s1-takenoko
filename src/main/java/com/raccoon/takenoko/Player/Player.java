package com.raccoon.takenoko.Player;

/**
 * Class representig the player taking part in the game. To be extended by a bot to
 * actually perform a move when it's its turn to play.
 * Will provide all the attributes and methods common to all players.
 */
public abstract class Player {

    // Signature to clarify, methode allowing the player to make his move
    public abstract void play();

}
