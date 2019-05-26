package pl.sudokusolver.solver.utility;

import org.junit.Assert;
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
    void usedInRow0TestTrue() {
        Assert.assertTrue(Utility.usedInRow(grid,0,0));
        Assert.assertTrue(Utility.usedInRow(grid,0,9));
        Assert.assertTrue(Utility.usedInRow(grid,0,8));
        Assert.assertTrue(Utility.usedInRow(grid,0,5));
        Assert.assertTrue(Utility.usedInRow(grid,0,3));
    }
    @Test
    void usedInRowTestIndexOutOfBoundException() {
        boolean thrown = false;

        try {
            Utility.usedInRow(grid,-1,0);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void usedInRow0TestFalse() {
        Assert.assertFalse(Utility.usedInRow(grid,0,1));
        Assert.assertFalse(Utility.usedInRow(grid,0,2));
        Assert.assertFalse(Utility.usedInRow(grid,0,4));
        Assert.assertFalse(Utility.usedInRow(grid,0,6));
        Assert.assertFalse(Utility.usedInRow(grid,0,7));
    }
    @Test
    void usedInRowTestTrue() {
        Assert.assertTrue(Utility.usedInRow(grid,0,0));
        Assert.assertTrue(Utility.usedInRow(grid,1,0));
        Assert.assertTrue(Utility.usedInRow(grid,2,0));
        Assert.assertTrue(Utility.usedInRow(grid,3,0));
        Assert.assertTrue(Utility.usedInRow(grid,4,0));
        Assert.assertTrue(Utility.usedInRow(grid,5,0));
        Assert.assertTrue(Utility.usedInRow(grid,6,0));
        Assert.assertTrue(Utility.usedInRow(grid,7,0));
        Assert.assertTrue(Utility.usedInRow(grid,8,0));
    }
    @Test
    void usedInRowTestFalse() {
        Assert.assertFalse(Utility.usedInRow(grid,0,1));
        Assert.assertFalse(Utility.usedInRow(grid,1,1));
        Assert.assertFalse(Utility.usedInRow(grid,2,1));
        Assert.assertFalse(Utility.usedInRow(grid,3,1));
        Assert.assertFalse(Utility.usedInRow(grid,4,1));
        Assert.assertFalse(Utility.usedInRow(grid,5,1));
        Assert.assertFalse(Utility.usedInRow(grid,6,2));
        Assert.assertFalse(Utility.usedInRow(grid,7,1));
        Assert.assertFalse(Utility.usedInRow(grid,8,1));

    }

    @Test
    void usedInCol0True() {
        Assert.assertTrue(Utility.usedInCol(grid,0,0));
        Assert.assertTrue(Utility.usedInCol(grid,0,5));
        Assert.assertTrue(Utility.usedInCol(grid,0,9));
        Assert.assertTrue(Utility.usedInCol(grid,0,7));
        Assert.assertTrue(Utility.usedInCol(grid,0,6));
    }

    @Test
    void usedInCol0False() {
        Assert.assertFalse(Utility.usedInCol(grid,0,1));
        Assert.assertFalse(Utility.usedInCol(grid,0,2));
        Assert.assertFalse(Utility.usedInCol(grid,0,3));
        Assert.assertFalse(Utility.usedInCol(grid,0,4));
        Assert.assertFalse(Utility.usedInCol(grid,0,8));
    }

    @Test
    void usedInColTestTrue() {
        Assert.assertTrue(Utility.usedInCol(grid,0,0));
        Assert.assertTrue(Utility.usedInCol(grid,1,0));
        Assert.assertTrue(Utility.usedInCol(grid,2,0));
        Assert.assertTrue(Utility.usedInCol(grid,3,0));
        Assert.assertTrue(Utility.usedInCol(grid,4,0));
        Assert.assertTrue(Utility.usedInCol(grid,5,0));
        Assert.assertTrue(Utility.usedInCol(grid,6,0));
        Assert.assertTrue(Utility.usedInCol(grid,7,0));
        Assert.assertTrue(Utility.usedInCol(grid,8,0));
    }
    @Test
    void usedInColTestFalse() {
        Assert.assertFalse(Utility.usedInCol(grid,0,-1));
        Assert.assertFalse(Utility.usedInCol(grid,1,-1));
        Assert.assertFalse(Utility.usedInCol(grid,2,-1));
        Assert.assertFalse(Utility.usedInCol(grid,3,-1));
        Assert.assertFalse(Utility.usedInCol(grid,4,-1));
        Assert.assertFalse(Utility.usedInCol(grid,5,-1));
        Assert.assertFalse(Utility.usedInCol(grid,6,-1));
        Assert.assertFalse(Utility.usedInCol(grid,7,-1));
        Assert.assertFalse(Utility.usedInCol(grid,8,-1));

    }

    @Test
    void usedInColTestIndexOutOfBoundException() {
        boolean thrown = false;

        try {
            Utility.usedInCol(grid,9,0);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    //usedInBox sprawdza czy cyfra znajduje sie w kwadracie 3x3
    @Test
    void usedInBox1True() {
        Assert.assertTrue(Utility.usedInBox(grid,0,0,0));
        Assert.assertTrue(Utility.usedInBox(grid,0,0,9));
        Assert.assertTrue(Utility.usedInBox(grid,0,0,8));
    }

    @Test
    void usedInBox1False() {
        Assert.assertFalse(Utility.usedInBox(grid,0,0,1));
        Assert.assertFalse(Utility.usedInBox(grid,0,0,2));
        Assert.assertFalse(Utility.usedInBox(grid,0,0,3));
        Assert.assertFalse(Utility.usedInBox(grid,0,0,4));
        Assert.assertFalse(Utility.usedInBox(grid,0,0,5));
        Assert.assertFalse(Utility.usedInBox(grid,0,0,6));
        Assert.assertFalse(Utility.usedInBox(grid,0,0,7));
    }

    @Test
    void usedInBoxTrue() {
        //test r5 c4
        Assert.assertTrue(Utility.usedInBox(grid,5,4,8));
        Assert.assertTrue(Utility.usedInBox(grid,5,4,6));
        Assert.assertTrue(Utility.usedInBox(grid,5,4,5));
        Assert.assertTrue(Utility.usedInBox(grid,5,4,0));
        //0 wystepuje w kazdym wyciętym
        Assert.assertTrue(Utility.usedInBox(grid,1,0,0));
        Assert.assertTrue(Utility.usedInBox(grid,0,1,0));
        Assert.assertTrue(Utility.usedInBox(grid,2,0,0));
        Assert.assertTrue(Utility.usedInBox(grid,0,2,0));
        Assert.assertTrue(Utility.usedInBox(grid,2,1,0));
        Assert.assertTrue(Utility.usedInBox(grid,1,2,0));
        Assert.assertTrue(Utility.usedInBox(grid,2,2,0));
        Assert.assertTrue(Utility.usedInBox(grid,6,6,0));
    }
    @Test
    void usedInBoxFalse() {
        //test r5 c4
        Assert.assertFalse(Utility.usedInBox(grid,5,4,1));
        Assert.assertFalse(Utility.usedInBox(grid,5,4,2));
        Assert.assertFalse(Utility.usedInBox(grid,5,4,3));
        Assert.assertFalse(Utility.usedInBox(grid,5,4,4));
        Assert.assertFalse(Utility.usedInBox(grid,5,4,7));
        Assert.assertFalse(Utility.usedInBox(grid,5,4,9));
        //1 nie ma nigdzie oprocz 4 kolumny
        Assert.assertFalse(Utility.usedInBox(grid,1,0,1));
        Assert.assertFalse(Utility.usedInBox(grid,0,5,1));
        Assert.assertFalse(Utility.usedInBox(grid,2,0,1));
        Assert.assertFalse(Utility.usedInBox(grid,3,5,1));
        Assert.assertFalse(Utility.usedInBox(grid,2,5,1));
        Assert.assertFalse(Utility.usedInBox(grid,1,6,1));
        Assert.assertFalse(Utility.usedInBox(grid,2,6,1));
        Assert.assertFalse(Utility.usedInBox(grid,6,6,1));
    }

    @Test   // INDEKS 7 - PROBLEM
    void usedInBoxIndexOutOfBoundExceptionForIndeks7() {
        boolean thrown1 = false;


        try {
            Utility.usedInBox(grid, 7, 0, 0);
        } catch (IndexOutOfBoundsException e) {
            thrown1 = true;
        }
        assertTrue(thrown1);
        try {
            Utility.usedInBox(grid, 0, 7, 0);
        } catch (IndexOutOfBoundsException e) {
            thrown1 = true;
        }
        assertTrue(thrown1);
        try {
            Utility.usedInBox(grid, 7, 7, 0);
        } catch (IndexOutOfBoundsException e) {
            thrown1 = true;
        }
        assertTrue(thrown1);
    }




        @Test   // wszystkie przypadki oprocz indeksu 7
    void usedInBoxIndexOutOfBoundException(){
        boolean thrown2 = false;
        boolean thrown3 = false;
        boolean thrown5 = false;
        boolean thrown6 = false;
        boolean thrown7 = false;


        try {
            Utility.usedInBox(grid,8,0,0);
        } catch (IndexOutOfBoundsException e) {
            thrown2 = true;
        }
        assertTrue(thrown2);
        try {
            Utility.usedInBox(grid,9,0,0);
        } catch (IndexOutOfBoundsException e) {
            thrown3 = true;
        }
        assertTrue(thrown3);

        try {
            Utility.usedInBox(grid,0,8,0);
        } catch (IndexOutOfBoundsException e) {
            thrown5 = true;
        }
        assertTrue(thrown5);
        try {
            Utility.usedInBox(grid,0,9,0);
        } catch (IndexOutOfBoundsException e) {
            thrown6 = true;
        }
        assertTrue(thrown6);
        try {
            Utility.usedInBox(grid,-1,-1,0);
        } catch (IndexOutOfBoundsException e) {
            thrown7 = true;
        }
        assertTrue(thrown7);

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