package pl.sudokusolver.app;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class GameBoardTest {

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

    int [][] player = new int[9][9];

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




    @Test
    void getSolution1() {
        GameBoard gameBoard = new GameBoard();
        Assert.assertArrayEquals(solution,gameBoard.getSolution());
    }

    @Test
    void getInitial() {
        GameBoard gameBoard = new GameBoard();
        Assert.assertArrayEquals(initial,gameBoard.getInitial());
    }

    @Test
    void getPlayer() {
        GameBoard gameBoard = new GameBoard();
        Assert.assertArrayEquals(initial,gameBoard.getInitial());
    }

    @Test
    void clear() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.clear();
        Assert.assertArrayEquals(gameBoard.getPlayer(),gameBoard.getInitial());
    }

    @Test
    void modifyPlayer() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.clear();
        gameBoard.modifyPlayer(solution);
        Assert.assertArrayEquals(gameBoard.getPlayer(),solution);
    }

    @Test
    void modifyInitial() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.modifyInitial(solution);
        Assert.assertArrayEquals(solution,gameBoard.getInitial());
    }

    @Test
    void modifyPlayer1() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.clear();
        gameBoard.modifyPlayer(1,0,0);
        gameBoard.modifyPlayer(10,1,5);
        gameBoard.modifyPlayer(5,4,4);
        gameBoard.modifyPlayer(3,5,2);
        gameBoard.modifyPlayer(2,3,6);
        gameBoard.modifyPlayer(1,2,4);

        int[][] myPlayer = new int[][]
                {
                        {1,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,1,0,0,0,0},
                        {0,0,0,0,0,0,2,0,0},
                        {0,0,0,0,5,0,0,0,0},
                        {0,0,3,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                };
        Assert.assertArrayEquals(myPlayer,gameBoard.getPlayer());

        gameBoard.modifyPlayer(15,5,2);
        gameBoard.modifyPlayer(-2,1,8);
        gameBoard.modifyPlayer(1,0,4);
        gameBoard.modifyPlayer(8,0,0);

        myPlayer[0][4] = 1;
        myPlayer[0][0] = 8;

        Assert.assertArrayEquals(myPlayer,gameBoard.getPlayer());


    }

    @Test
    void modifyInitial1() {
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

    @Test
    void checkForSuccess() {
        GameBoard gameBoard = new GameBoard();

        int[][] myInitial = new int[][]
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

        gameBoard.modifyInitial(myInitial);
        Assert.assertTrue(gameBoard.checkForSuccess());

        gameBoard.modifyInitial(0,5,5);
        gameBoard.modifyInitial(0,2,0);
        gameBoard.modifyInitial(0,0,2);

        int[][] myPlayer = new int[][]
                {
                        {5,3,8,4,6,1,7,9,2},
                        {6,9,7,3,2,5,8,1,4},
                        {2,1,4,7,8,9,5,6,3},
                        {9,4,1,2,7,8,6,3,5},
                        {7,6,2,1,5,3,9,4,8},
                        {8,5,3,9,4,0,1,2,7},
                        {3,8,9,5,1,2,4,7,6},
                        {4,2,6,8,9,7,3,5,1},
                        {1,7,5,6,3,4,2,8,9}
                };

        gameBoard.modifyPlayer(myPlayer);

        Assert.assertFalse(gameBoard.checkForSuccess());

    }

    @Test
    void checkForSuccessGeneral() {

        GameBoard gameBoard = new GameBoard();

        int[][] myInitial = new int[][]
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


        int[][] myPlayer = new int[][]
                {
                        {5,3,0,4,0,0,0,9,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,1,0,0,0,0,0,0,0},
                        {9,0,0,0,7,0,6,0,0},
                        {0,0,2,0,0,0,0,4,0},
                        {0,5,0,9,0,0,0,2,0},
                        {3,0,9,0,0,0,4,0,6},
                        {0,2,0,0,0,0,3,0,0},
                        {1,0,5,0,3,0,0,0,9}
                };

        gameBoard.modifyPlayer(myPlayer);
        gameBoard.modifyInitial(myInitial);

        Assert.assertTrue(gameBoard.checkForSuccessGeneral());


        myPlayer = new int[][]
                {
                        {5,3,0,1,0,0,0,9,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,1,0,0,0,0,0,0,0},
                        {9,0,0,0,7,0,6,0,0},
                        {0,0,2,0,0,0,0,4,0},
                        {0,5,0,9,0,0,0,2,0},
                        {3,0,9,0,0,0,2,0,6},
                        {0,2,0,0,0,0,3,0,0},
                        {1,0,5,0,3,0,0,0,9}
                };

        gameBoard.modifyPlayer(myPlayer);
        gameBoard.modifyInitial(myInitial);

        Assert.assertFalse(gameBoard.checkForSuccessGeneral());


    }

}