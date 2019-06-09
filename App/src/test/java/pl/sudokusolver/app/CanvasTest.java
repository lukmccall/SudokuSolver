package pl.sudokusolver.app;

import org.junit.Assert;
import org.junit.Test;
import pl.sudokusolver.app.CanvasStub;
import pl.sudokusolver.app.CustomViews.Canvas;

import static org.junit.Assert.*;

public class CanvasTest {

    @Test
    public void getInitial() {
        Canvas canvas = new CanvasStub();

        int[][] myInitial =
                {
                        {7,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,3,0,0},
                        {0,5,0,1,0,0,0,0,4},
                        {0,0,0,0,0,8,0,0,0},
                        {0,0,0,6,0,0,0,0,0},
                        {2,0,0,0,0,1,0,0,0},
                        {0,2,0,0,0,0,0,0,0},
                        {0,0,0,3,0,0,0,0,0},
                        {9,0,0,4,0,0,0,0,8},
                };

        canvas.modifyInitial(myInitial);

        try{
            Assert.assertArrayEquals(myInitial,canvas.getInitial());
        }
        catch(IllegalArgumentException io ){
            io.printStackTrace();
        }
    }

    @Test
    public void clear() {
        Canvas canvas = new CanvasStub();

        int[][] clearArray = new int[][]
                {
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                };


        int[][] myInitial =
                {
                        {0,0,8,0,6,1,7,0,2},
                        {1,9,7,3,2,5,8,1,4},
                        {2,0,4,7,8,9,5,6,3},
                        {3,4,1,2,0,8,0,3,5},
                        {7,6,0,1,5,3,9,0,8},
                        {8,0,3,0,4,6,1,0,7},
                        {0,8,0,5,1,2,0,7,0},
                        {4,0,6,8,9,7,0,4,1},
                        {0,7,0,3,0,4,2,8,0}
                };
        canvas.modifyInitial(myInitial);
        canvas.clear();

        Assert.assertArrayEquals(clearArray,canvas.getInitial());
    }

    @Test
    public void getOffsetY() {
        Canvas canvas = new CanvasStub();
        Assert.assertEquals(0,canvas.getOffsetY(),0.0);
    }

    @Test
    public void modifySolution() {
        Canvas canvas = new CanvasStub();
        int[][] mySolution =
                {
                        {8,9,2,5,7,3,6,1,4},
                        {7,4,6,9,2,1,8,3,5},
                        {3,1,5,4,6,8,9,7,2},
                        {4,5,7,6,8,2,3,9,1},
                        {9,6,8,1,3,5,2,4,7},
                        {1,2,3,7,4,9,5,6,8},
                        {2,7,1,8,9,6,4,5,3},
                        {5,3,9,2,1,4,7,8,6},
                        {6,8,4,3,5,7,1,2,9}
                };
        canvas.modifyInitial(mySolution);

        try {
            Assert.assertArrayEquals(mySolution, canvas.getInitial());
        }
        catch (IllegalArgumentException io){
            io.printStackTrace();
        }
    }

    @Test
    public void modifyInitial() {
        Canvas canvas = new CanvasStub();
        int[][] myInitial =
                {
                        {0,0,0,0,0,0,0,0,0},
                        {0,5,0,0,0,6,0,0,1},
                        {0,0,9,5,0,1,4,0,2},
                        {0,3,7,9,0,5,0,0,0},
                        {5,8,1,0,2,7,9,0,0},
                        {0,0,0,4,0,8,1,0,7},
                        {0,0,0,0,0,0,0,0,4},
                        {0,0,4,0,0,0,6,0,9},
                        {9,0,0,8,7,4,2,1,0}
                };
        canvas.modifyInitial(myInitial);

        try {
            Assert.assertArrayEquals(myInitial, canvas.getInitial());
        }
        catch (IllegalArgumentException io){
            io.printStackTrace();
        }
    }


    @Test
    public void onValueInserted() {
        Canvas canvas = new CanvasStub();
        int[][] myInitial =
                {
                        {0,0,0,0,0,0,0,0,0},
                        {0,5,0,0,0,6,0,0,1},
                        {0,0,9,5,0,1,4,0,2},
                        {0,3,7,9,0,5,0,0,0},
                        {5,8,1,0,2,7,9,0,0},
                        {0,0,0,4,0,8,1,0,7},
                        {0,0,0,0,0,0,0,0,4},
                        {0,0,4,0,0,0,6,0,9},
                        {9,0,0,8,7,4,2,1,0}
                };
        canvas.modifyInitial(myInitial);
        canvas.mouseClick(50,50);
        canvas.onValueInserted(2);

        myInitial [ 1 ] [ 1 ] = 2;

        try {
            Assert.assertArrayEquals(myInitial, canvas.getInitial());
        }
        catch (IllegalArgumentException io){
            io.printStackTrace();
        }


    }
}