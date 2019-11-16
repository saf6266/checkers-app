package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag ("Application-Tier")
public class PlayerTest {

    private final static Player PLAYER = new Player("test");
    private final static Player OPPONENT = new Player("opponent");

    @Test
    public void testPlayerName_valid(){

        final Player CuT = new Player("Billy");

        //Check if the name is valid
        assertTrue(CuT.validName());

        final Player P = new Player("&(&");
        assertFalse(P.validName());

        final Player P1 = new Player("");
        assertFalse(P1.validName());

    }

    /*
    Tests whether the color is properly changing
     */
    @Test
    public void testColor_(){
        final Player CuT = new Player("test");

        //The color should be initiated to None
        assertFalse(CuT.isRed());
        assertFalse(CuT.isWhite());

        //Set the color to red
        CuT.setRed();

        assertFalse(CuT.isWhite());
        assertTrue(CuT.isRed());

        //Set the color to white
        CuT.setWhite();

        assertFalse(CuT.isRed());
        assertTrue(CuT.isWhite());


        //The color should be reset to None

        CuT.setNone();
        assertFalse(CuT.isRed());
        assertFalse(CuT.isWhite());
    }

    /*
    Test the ba
     */
    @Test
    public void testTurn(){
        final Player CuT = new Player("Bob");

        assertFalse(CuT.isTurn());

        CuT.setTurn(true);

        assertTrue(CuT.isTurn());

    }

}
