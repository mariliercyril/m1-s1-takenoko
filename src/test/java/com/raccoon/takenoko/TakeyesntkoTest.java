package com.raccoon.takenoko;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.Game;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TakeyesntkoTest {

    @Before
    public void setup() {
        Takeyesntko.setVerbose(false);
    }

    @Test
    public void plays1000games() {
        try {
            assertEquals("Did not launch the 1000 games or counting failed", 1000, Takeyesntko.launch1000gamesNoJutsu());
        }catch(Throwable e){
            fail("Launched 1000 games, exception raised : " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    public void gameGoesWell() {
        try {
            Takeyesntko.launch1gameNoJutsu(new Game());
        } catch (Exception e) {
            fail("Game didn't go well, exception raised : " + e.getClass().getSimpleName() + ". See stack : \n" + Arrays.deepToString(e.getStackTrace()));
        }
    }

    @Test
    public void testPrint_withDefaultVerbose() {

    	try {
    		Takeyesntko.print("Test!");
    	} catch (Throwable e) {
            fail(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    public void testPrint_withFalseVerbose() {

    	Takeyesntko.setVerbose(false);

    	try {
    		Takeyesntko.print("Test!");
    	} catch (Throwable e) {
            fail(Arrays.toString(e.getStackTrace()));
        }
    }

}
