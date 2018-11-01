package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;

import java.awt.*;

public class Panda {
    private Point position;

    public Panda() {
        this.position = new Point(0, 0);
    }

    public Point getPosition() {
        return position;
    }

    /**
     * Allows us to tell the panda to move to a given location.
     *
     * @param position The location where we want the panda to go
     */
    public void move(Board board, Point position) {
        Takeyesntko.print("Panda moves from " + this.position + " to " + position);
        this.position = position;
        this.eat(board, this.position); // Eat the bamboo where the panda lands
    }

    private void eat(Board board, Point position) {
        if (!board.get(position).isEnclosed()) {    // The panda can't eat if there is an enclosure
            board.get(position).decreaseBambooSize();
        }
    }
}
