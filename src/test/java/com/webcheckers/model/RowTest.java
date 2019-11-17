package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Tag("model-Tier")
public class RowTest {

    @Test
    public void test_GenerateSpaces(){
        final ArrayList<Space> ExpectedList = new ArrayList<Space>() ;
        final Row Cut = new Row(1);
        //assertSame(ExpectedList, Cut.generateSpaces(spaceList, 1));
    }
}
