package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;

import java.awt.*;
import java.util.List;

public class Panda {
    private Point position;

    public Panda() {
        this.position = new Point(0, 0);
    }

    public Point getPosition() {
        return position;
    }

    /**
     * This methods allows us to tell the gardener to move to a given location
     * @param position The location where we want the panda to go
     */
    public void move(Board board, Point position) {
        Takeyesntko.print("Gardener moves from " + this.position + " to " + position);
        this.position = position;
        this.eat(board, this.position); // Eat the bamboo where the panda lands
    }

    private void eat(Board board, Point position) {
        board.get(position).decreaseBambooSize();
    }
}
