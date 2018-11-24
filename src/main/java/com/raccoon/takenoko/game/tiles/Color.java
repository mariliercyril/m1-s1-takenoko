package com.raccoon.takenoko.game.tiles;

public enum Color {
    GREEN(11,"G"), YELLOW(9,"Y"), PINK(7,"P");    // The tiles come in three possible colors 11, 9, 7

    // number of tiles to generate in that color
    int quantite;
    String symbol;

    Color(int q, String symbol) {
        this.quantite = q;
        this.symbol = symbol;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
