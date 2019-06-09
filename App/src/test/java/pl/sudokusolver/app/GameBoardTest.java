package pl.sudokusolver.app;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {

    @Test
    public void getInitial() {
        int[][] initial = new int[][]
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
        GameBoard gameBoard = new GameBoard();
        Assert.assertArrayEquals(initial,gameBoard.getInitial());
    }

    @Test
    public void getSolution() {
        int[][] solution = new int[][]
                {
                        {5,3,8,4,6,1,7,9,2},
                        {6,9,7,3,2,5,8,1,4},
                        {2,1,4,7,8,9,5,6,3},
                        {9,4,1,2,7,8,6,3,5},
                        {7,6,2,1,5,3,9,4,8},
                        {8,5,3,9,4,6,1,2,7},
                        {3,8,9,5,1,2,4,7,6},
                        {4,2,6,8,9,7,3,5,1},
                        {1,7,5,6,3,4,2,8,9}
                };
        GameBoard gameBoard = new GameBoard();
        gameBoard.modifySolution(solution);
        Assert.assertArrayEquals(solution,gameBoard.getSolution());
    }

    @Test
    public void clear() {
        int[][] solution = new int[][]
                {
                        {5,3,8,4,6,1,7,9,2},
                        {6,9,7,3,2,5,8,1,4},
                        {2,1,4,7,8,9,5,6,3},
                        {9,4,1,2,7,8,6,3,5},
                        {7,6,2,1,5,3,9,4,8},
                        {8,5,3,9,4,6,1,2,7},
                        {3,8,9,5,1,2,4,7,6},
                        {4,2,6,8,9,7,3,5,1},
                        {1,7,5,6,3,4,2,8,9}
                };

        int[][] initial = new int[][]
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
        GameBoard gameBoard = new GameBoard();
        gameBoard.modifySolution(solution);

        gameBoard.clear();
        Assert.assertArrayEquals(initial,gameBoard.getSolution());

        gameBoard.modifyInitial(solution);
        gameBoard.clear();
        Assert.assertArrayEquals(initial,gameBoard.getSolution());

    }

    @Test
    public void modifySolution() {
        int[][] solution = new int[][]
                {
                        {5,3,8,4,6,1,7,9,2},
                        {6,9,7,3,2,5,8,1,4},
                        {2,1,4,7,8,9,5,6,3},
                        {9,4,1,2,7,8,6,3,5},
                        {7,6,2,1,5,3,9,4,8},
                        {8,5,3,9,4,6,1,2,7},
                        {3,8,9,5,1,2,4,7,6},
                        {4,2,6,8,9,7,3,5,1},
                        {1,7,5,6,3,4,2,8,9}
                };
        GameBoard gameBoard = new GameBoard();
        gameBoard.modifySolution(solution);

        Assert.assertEquals(solution,gameBoard.getSolution());

    }

    @Test
    public void modifyInitial() {
        int[][] myInitial =
                {
                        {0,0,8,0,6,1,7,0,2},
                        {6,9,7,3,2,5,8,1,4},
                        {2,0,4,7,8,9,5,6,3},
                        {0,4,1,2,0,8,0,3,5},
                        {7,6,0,1,5,3,9,0,8},
                        {8,0,3,0,4,6,1,0,7},
                        {0,8,0,5,1,2,0,7,0},
                        {4,0,6,8,9,7,0,5,1},
                        {0,7,0,6,0,4,2,8,0}
                };

        GameBoard gameBoard = new GameBoard();
        gameBoard.modifyInitial(myInitial);
        Assert.assertArrayEquals(myInitial,gameBoard.getInitial());
    }

    @Test
    public void modifyInitial1() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.modifyInitial(10,0,0);
        gameBoard.modifyInitial(0,1,5);
        gameBoard.modifyInitial(-4,4,4);
        gameBoard.modifyInitial(5,4,4);
        gameBoard.modifyInitial(8,4,4);
        gameBoard.modifyInitial(3,5,2);
        gameBoard.modifyInitial(2,3,8);
        gameBoard.modifyInitial(1,8,4);
        gameBoard.modifyInitial(-6,5,7);
        gameBoard.modifyInitial(15,1,6);
        gameBoard.modifyInitial(5,6,3);

        int[][] myInitial = new int[][]
                {
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,2},
                        {0,0,0,0,8,0,0,0,0},
                        {0,0,3,0,0,0,0,0,0},
                        {0,0,0,5,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,1,0,0,0,0},
                };
        Assert.assertArrayEquals(myInitial,gameBoard.getInitial());
    }
}