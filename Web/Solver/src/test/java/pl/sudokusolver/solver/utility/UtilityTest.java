package pl.sudokusolver.solver.utility;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    int grid[][] = { { 0, 9, 0, 0, 0, 0, 8, 5, 3 },
            { 0, 0, 0, 8, 0, 0, 0, 0, 4 },
            { 0, 0, 8, 2, 0, 3, 0, 6, 9 },
            { 5, 7, 4, 0, 0, 2, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 9, 0, 0, 6, 3, 7 },
            { 9, 4, 0, 1, 0, 8, 5, 0, 0 },
            { 7, 0, 0, 0, 0, 6, 0, 0, 0 },
            { 6, 8, 2, 0, 0, 0, 0, 9, 0 } };

    @Test
    void usedInRow1() {


        Assert.assertTrue(Utility.usedInRow(grid,0,0));

    }

    @Test
    void usedInCol1() {
    }

    @Test
    void usedInBox1() {
    }

    @Test
    void canPlaceDigit1() {
    }

    @Test
    void getUnassignedLocation1() {
    }

    @Test
    void gridToString1() {
    }

    @Test
    void usedInRow() {
    }

    @Test
    void usedInCol() {
    }

    @Test
    void usedInBox() {
    }

    @Test
    void canPlaceDigit() {
    }

    @Test
    void getUnassignedLocation() {
    }

    @Test
    void gridToString() {
    }

}