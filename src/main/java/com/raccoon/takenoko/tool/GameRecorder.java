package com.raccoon.takenoko.tool;


import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.tiles.Tile;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class GameRecorder {
    private PrintWriter writer;
    private boolean recording;
    private static int recordedGames = 0;

    public GameRecorder() {recording=false;}

    public void startRecording() {
        try {
            writer = new PrintWriter("ter_display/data/game-" + recordedGames + ".txt", "UTF-8");
            recording = true;
            recordedGames++;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void recordStep(Game game, int turn, int player) {
        if (recording) {
            writer.println("TRN\t" + turn + "\t" + player);
            for (Tile t : game.getBoard().getAllTiles()) {
                writer.println("TIL\t" + t.getPosition().getX() + "\t" + t.getPosition().getY() + "\t" + colorChar(t) + "\t" + irrigatedChar(t) + "\t" + t.getBambooSize());
            }
            writer.println("PDA\t" + game.getPanda().getPosition().getX() + "\t" + game.getPanda().getPosition().getY());
            writer.println("GRD\t" + game.getGardener().getPosition().getX() + "\t" + game.getGardener().getPosition().getY());
            writer.println("###");
        }
    }

    private String colorChar(Tile t) {
        if (Objects.isNull(t.getColor())) {
            return "BLUE";
        }
        return t.getColor().toString();
    }

    private String irrigatedChar(Tile t) {  // returns "I" if the tile is irrigated and "D" otherwise
        if (t.isIrrigated()) {
            return "I";
        } else {
            return "D";
        }
    }

    public void stopRecording() {
        if (recording) {
            writer.close();
        }
    }
}
