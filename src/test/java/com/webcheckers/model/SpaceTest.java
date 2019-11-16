package com.webcheckers.model;


import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("Model-Tier")
public class SpaceTest {

    private final static int cellIdx = 5;
    private final static Piece piece = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);
    private final static boolean isDark = true;

    @Test
    public void testSpace_is_Valid_space_toMove(){
        final Space CuT = new Space(piece, cellIdx, isDark);

        assertFalse(CuT.isValid());
    }


    @Test
    public void testSpace_is_dark(){
        final Space CuT = new Space(piece, cellIdx, isDark);

        assertTrue(CuT.isDark());
    }
}
