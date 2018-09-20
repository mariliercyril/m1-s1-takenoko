package com.raccoon.takenoko.Game;

import java.awt.*;
import java.util.List;

public class StubTile implements Tile {
    private Point position;
    private int nbFreeBorders;

    public StubTile(){
        position = null;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public int getFreeBorders() {
        return nbFreeBorders;
    }

    @Override
    public void setFreeBorders(int numberOfFreeNumbers) {
        nbFreeBorders = numberOfFreeNumbers;
    }
}
