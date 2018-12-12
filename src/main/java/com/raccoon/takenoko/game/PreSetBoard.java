package com.raccoon.takenoko.game;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service("preSetBoard")
@Scope("prototype")
public class PreSetBoard extends HashBoard {


    public PreSetBoard(@Value("#{systemProperties['boardFile']}") String filename) {


        super();

        if (filename == null) {
            randomFill();
        }
        else {

            try(BufferedReader br = new BufferedReader(new FileReader(filename))) { // Try with resources (auto close)
                String line = br.readLine();

                while (line != null && !line.equals("###")) {
                    Tools.parseLine(line, this);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void randomFill() {
        /*
        Here we must be able to specify somehow constraints about how this board will be filled
        E.G. we can't have hard-coded the amount of bamboos of each colour
         */

        List<Tile> tiles = new ArrayList<>();

        /*
        For now we just fill a list with 10 tiles of each colour
        There is a bamboo of size 4 on each of them
         */
        for (int i = 0; i < 2; i++) {
            Tile newTile = new Tile(Color.GREEN);
            newTile.setBambooSize(100);
            tiles.add(newTile);
        }
        for (int i = 0; i < 2; i++) {
            Tile newTile = new Tile(Color.PINK);
            newTile.setBambooSize(100);
            tiles.add(newTile);
        }
        for (int i = 0; i < 2; i++) {
            Tile newTile = new Tile(Color.YELLOW);
            newTile.setBambooSize(100);
            tiles.add(newTile);
        }

        for (int i = 0; i < 9; i++) {
            Tile newTile = new Tile(Color.GREEN);
            tiles.add(newTile);
        }
        for (int i = 0; i < 5; i++) {
            Tile newTile = new Tile(Color.PINK);
            tiles.add(newTile);
        }
        for (int i = 0; i < 7; i++) {
            Tile newTile = new Tile(Color.YELLOW);
            tiles.add(newTile);
        }

        // We shuffle the list
        Collections.shuffle(tiles);

        // We put each tile on the first available position
        for(Tile tile : tiles) {
            this.set(this.getAvailablePositions().get(0), tile);
        }
    }

}
