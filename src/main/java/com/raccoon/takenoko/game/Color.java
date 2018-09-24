package com.raccoon.takenoko.game;

public enum Color {
    GREEN(11), YELLOW(9), PINK(7);    // The tiles come in three possible colors 11, 9, 7

    // tiles to generate in that color
    int quantite;

    Color(int q) {
        quantite = q;
    }

    public int getQuantite() {
        return quantite;
    }
}
