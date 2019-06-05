package pl.sudokusolver.solver.utility;

import javafx.util.Pair;
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

    int grid2[][] = { { 1, 9, 1, 1, 1, 1, 8, 5, 3 },
            { 8, 6, 5, 8, 1, 1, 1, 1, 4 },
            { 2, 3, 8, 2, 7, 3, 2, 6, 9 },
            { 5, 7, 4, 1, 2, 2, 3, 4, 5 },
            { 8, 6, 5, 8, 1, 1, 1, 1, 4 },
            { 2, 3, 8, 2, 7, 3, 2, 6, 9 },
            { 5, 7, 4, 1, 2, 2, 3, 4, 5 },
            { 5, 7, 4, 1, 2, 2, 3, 4, 5 },
            { 8, 6, 5, 8, 1, 1, 1, 1, 4 } };

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

        assertThrows(IndexOutOfBoundsException.class, ()->{
            Utility.canPlaceDigit(grid, -1, -1, 0);
        });
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
        assertThrows(IndexOutOfBoundsException.class, ()->{
            Utility.usedInCol(grid,9,0);
        });
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


    @Test
    void usedInBoxIndexOutOfBoundException(){
        assertThrows(IndexOutOfBoundsException.class, ()->{
            Utility.usedInBox(grid,9,0,0);
        });

        assertThrows(IndexOutOfBoundsException.class, ()->{
            Utility.usedInBox(grid,0,9,0);
        });

        assertThrows(IndexOutOfBoundsException.class, ()->{
            Utility.usedInBox(grid,-1,-1,0);
        });

    }

    @Test
    void canPlaceDigitTestTrue() {  // sprawdza czy nie ma takiej liczby w kolumnie,wierszu,boksie
        assertTrue(Utility.canPlaceDigit(grid,0,0,1));
        assertTrue(Utility.canPlaceDigit(grid,1,1,1));
        assertTrue(Utility.canPlaceDigit(grid,2,2,1));
        assertTrue(Utility.canPlaceDigit(grid,0,4,1));
        assertTrue(Utility.canPlaceDigit(grid,8,8,1));
        assertTrue(Utility.canPlaceDigit(grid,7,7,1));
        assertTrue(Utility.canPlaceDigit(grid,0,5,1));
    }

    @Test
    void canPlaceDigitTestFalse() {  // sprawdza czy jest taka liczba w kolumnie,wierszu,boksie
        assertFalse(Utility.canPlaceDigit(grid,0,0,0));
        assertFalse(Utility.canPlaceDigit(grid,1,1,0));
        assertFalse(Utility.canPlaceDigit(grid,2,2,0));
        assertFalse(Utility.canPlaceDigit(grid,0,4,0));
        assertFalse(Utility.canPlaceDigit(grid,8,8,9));
        assertFalse(Utility.canPlaceDigit(grid,7,7,5));
        assertFalse(Utility.canPlaceDigit(grid,0,5,8));
    }

    @Test
    void canPlaceDigitIndexOutOfBoundExceptionTest() {

        assertThrows(IndexOutOfBoundsException.class, ()->{
            Utility.canPlaceDigit(grid, -1, -1, 0);
        });

        assertThrows(IndexOutOfBoundsException.class, ()->{
            Utility.canPlaceDigit(grid, 9, 6, 0);
        });

        assertThrows(IndexOutOfBoundsException.class, ()->{
            Utility.canPlaceDigit(grid, 5, 9, 0);
        });

    }


    @Test
    void getUnassignedLocationTrue() {
        Pair<Integer,Integer> a = Utility.getUnassignedLocation(grid);
        boolean test = false;

        if (a.equals(new Pair<>(0,0))) test = true;
        assertTrue(test);
    }


    @Test
    void getUnassignedLocationFalse() {
        Pair<Integer,Integer> a = Utility.getUnassignedLocation(grid);
        boolean test = false;

        if (a.equals(new Pair<>(7,7))) test = true;
        assertFalse(test);
    }


    @Test
    void getUnassignedLocation9x9() {

        Pair<Integer,Integer> a = Utility.getUnassignedLocation(grid2);
        boolean test = false;

        if (a.equals(new Pair<>(9,9))) test = true;

        assertTrue(test);
    }

    @Test
    void getUnassignedLocationGrid2True() {
        int [][] testGrid = grid2.clone();
        testGrid[5][5]=0;
        Pair<Integer,Integer> a = Utility.getUnassignedLocation(testGrid);
        boolean test = false;

        if (a.equals(new Pair<>(5,5))) test = true;
        assertTrue(test);
    }



    @Test
    void gridToStringTrue() {

      StringBuilder grid1 = new StringBuilder();
      grid1.append("[0, 9, 0, 0, 0, 0, 8, 5, 3]").append(System.getProperty("line.separator"))
              .append("[0, 0, 0, 8, 0, 0, 0, 0, 4]").append(System.getProperty("line.separator"))
        .append("[0, 0, 8, 2, 0, 3, 0, 6, 9]").append(System.getProperty("line.separator"))
        .append("[5, 7, 4, 0, 0, 2, 0, 0, 0]").append(System.getProperty("line.separator"))
        .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
        .append("[0, 0, 0, 9, 0, 0, 6, 3, 7]").append(System.getProperty("line.separator"))
        .append("[9, 4, 0, 1, 0, 8, 5, 0, 0]").append(System.getProperty("line.separator"))
        .append("[7, 0, 0, 0, 0, 6, 0, 0, 0]").append(System.getProperty("line.separator"))
        .append("[6, 8, 2, 0, 0, 0, 0, 9, 0]").append(System.getProperty("line.separator"));

        assertEquals(grid1.toString(),Utility.gridToString(grid));

    }

}