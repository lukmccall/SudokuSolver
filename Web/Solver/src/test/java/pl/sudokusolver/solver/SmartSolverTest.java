package pl.sudokusolver.solver;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.sudokusolver.solver.utility.Utility;

import static org.junit.jupiter.api.Assertions.*;

class SmartSolverTest {

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
    void preSolve(){
        int grid[][] = { { 0, 2, 3, 4, 5, 6, 7, 8, 9 },
                { 4, 5, 6, 7, 8, 9, 1, 2, 3},
                { 7, 8, 9, 1, 0, 3, 4, 5, 6 },
                { 2, 1, 4, 3, 0, 5, 8, 9, 7 },
                { 3, 6, 5, 8, 9, 7, 0, 1, 4 },
                { 8, 0, 7, 2, 1, 4, 3, 6, 5 },
                { 0, 3, 1, 6, 4, 2, 9, 7, 8 },
                { 6, 4, 2, 0, 7, 8, 5, 3, 1 },
                { 9, 7, 8, 5, 3, 1, 6, 4, 0 } };
        SmartSolver smartSolver = new SmartSolver();

        Assert.assertTrue("This sudoku is correct",smartSolver.preSolve(grid));
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                if(grid[i][j] == 0) Assert.fail("Sudoku should be solved");

    }

    @Test
    void solveSudoku(){
        int grid[][] = { { 0, 9, 0, 0, 0, 0, 8, 5, 3 },
                { 0, 0, 0, 8, 0, 0, 0, 0, 4 },
                { 0, 0, 8, 2, 0, 3, 0, 6, 9 },
                { 5, 7, 4, 0, 0, 2, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 9, 0, 0, 6, 3, 7 },
                { 9, 4, 0, 1, 0, 8, 5, 0, 0 },
                { 7, 0, 0, 0, 0, 6, 0, 0, 0 },
                { 6, 8, 2, 0, 0, 0, 0, 9, 0 } };

        StringBuilder ss = new StringBuilder();
        ss.append("[2, 9, 7, 6, 1, 4, 8, 5, 3]").append(System.getProperty("line.separator"));
        ss.append("[1, 3, 6, 8, 5, 9, 7, 2, 4]").append(System.getProperty("line.separator"));
        ss.append("[4, 5, 8, 2, 7, 3, 1, 6, 9]").append(System.getProperty("line.separator"));
        ss.append("[5, 7, 4, 3, 6, 2, 9, 1, 8]").append(System.getProperty("line.separator"));
        ss.append("[3, 6, 9, 7, 8, 1, 2, 4, 5]").append(System.getProperty("line.separator"));
        ss.append("[8, 2, 1, 9, 4, 5, 6, 3, 7]").append(System.getProperty("line.separator"));
        ss.append("[9, 4, 3, 1, 2, 8, 5, 7, 6]").append(System.getProperty("line.separator"));
        ss.append("[7, 1, 5, 4, 9, 6, 3, 8, 2]").append(System.getProperty("line.separator"));
        ss.append("[6, 8, 2, 5, 3, 7, 4, 9, 1]").append(System.getProperty("line.separator"));
        String comp = ss.toString();

        SmartSolver smartSolver = new SmartSolver();

        assertTrue(smartSolver.solve(grid));

        String result = Utility.gridToString(grid);
        assertEquals(comp,result);
    }

    @Test
    void solveSudokuTrueAndEquals(){
        //sprawdzenie czy algorytm dziala zgodnie z oczekiwaniem
        int gridOnlyZeros[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };


        StringBuilder s = new StringBuilder();
        s.append("[1, 2, 3, 4, 5, 6, 7, 8, 9]").append(System.getProperty("line.separator"));
        s.append("[4, 5, 6, 7, 8, 9, 1, 2, 3]").append(System.getProperty("line.separator"));
        s.append("[7, 8, 9, 1, 2, 3, 4, 5, 6]").append(System.getProperty("line.separator"));
        s.append("[2, 1, 4, 3, 6, 5, 8, 9, 7]").append(System.getProperty("line.separator"));
        s.append("[3, 6, 5, 8, 9, 7, 2, 1, 4]").append(System.getProperty("line.separator"));
        s.append("[8, 9, 7, 2, 1, 4, 3, 6, 5]").append(System.getProperty("line.separator"));
        s.append("[5, 3, 1, 6, 4, 2, 9, 7, 8]").append(System.getProperty("line.separator"));
        s.append("[6, 4, 2, 9, 7, 8, 5, 3, 1]").append(System.getProperty("line.separator"));
        s.append("[9, 7, 8, 5, 3, 1, 6, 4, 2]").append(System.getProperty("line.separator"));
        String comp1 = s.toString();

        ISolver smartSolver = new SmartSolver();
        Assert.assertTrue(smartSolver.solve(gridOnlyZeros));

        String result2 = Utility.gridToString(gridOnlyZeros);
        Assert.assertEquals(comp1,result2);
    }

    @Test
    void solveSudokuTrueAndNotEquals(){

        ISolver smartSolver = new SmartSolver();
        Assert.assertTrue(smartSolver.solve(grid));

        StringBuilder ss = new StringBuilder();
        ss.append("[1, 1, 7, 6, 1, 4, 8, 5, 2]").append(System.getProperty("line.separator"));
        ss.append("[1, 3, 6, 8, 5, 9, 7, 2, 2]").append(System.getProperty("line.separator"));
        ss.append("[3, 6, 9, 7, 8, 1, 2, 4, 2]").append(System.getProperty("line.separator"));
        ss.append("[5, 7, 4, 3, 6, 2, 9, 1, 2]").append(System.getProperty("line.separator"));
        ss.append("[3, 6, 9, 7, 8, 1, 2, 4, 2]").append(System.getProperty("line.separator"));
        ss.append("[8, 2, 1, 9, 4, 5, 6, 3, 2]").append(System.getProperty("line.separator"));
        ss.append("[9, 4, 3, 1, 2, 8, 5, 7, 2]").append(System.getProperty("line.separator"));
        ss.append("[1, 1, 7, 6, 1, 4, 8, 5, 2]").append(System.getProperty("line.separator"));
        ss.append("[3, 6, 9, 7, 8, 1, 2, 4, 2]").append(System.getProperty("line.separator"));

        String result = Utility.gridToString(grid);
        Assert.assertNotEquals(ss.toString(),result);

    }

    @Test
    void solveSudokuFalse() {
        int grid2[][] = {{9, 9, 0, 0, 0, 0, 8, 5, 3},
                {9, 0, 0, 8, 0, 0, 0, 0, 4},
                {9, 0, 8, 2, 0, 3, 0, 6, 9},
                {9, 7, 4, 0, 0, 2, 0, 0, 0},
                {9, 0, 0, 0, 0, 0, 0, 0, 0},
                {9, 0, 0, 9, 0, 0, 6, 3, 7},
                {9, 4, 0, 1, 0, 8, 5, 0, 0},
                {9, 0, 0, 0, 0, 6, 0, 0, 0},
                {9, 8, 2, 0, 0, 0, 0, 9, 0}};

        // tylko jedna liczba ktora nie daje mozliwosci rozwiazania
        int grid3[][] = {{0, 9, 0, 0, 0, 0, 8, 5, 3},
                {0, 0, 0, 8, 0, 0, 0, 0, 4},
                {0, 0, 8, 2, 0, 3, 0, 6, 9},
                {5, 7, 4, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 9, 9, 0, 6, 3, 7},
                {9, 4, 0, 1, 0, 8, 5, 0, 0},
                {7, 0, 0, 0, 0, 6, 0, 0, 0},
                {6, 8, 2, 0, 0, 0, 0, 9, 0}};

        int grid4[][] = {{1, 0, 0, 0, 0, 0, 8, 5, 3},
                {8, 6, 5, 0, 1, 0, 0, 1, 0},
                {2, 3, 8, 2, 7, 3, 2, 6, 9},
                {0, 0, 4, 1, 2, 2, 3, 4, 5},
                {8, 6, 5, 8, 1, 1, 1, 1, 4},
                {0, 3, 8, 2, 0, 3, 2, 6, 9},
                {5, 7, 0, 0, 2, 2, 3, 0, 0},
                {0, 7, 4, 1, 2, 2, 3, 4, 5},
                {0, 6, 0, 8, 1, 1, 1, 1, 4}};

        // też tylko jedna liczba, przez którą nie mozna rozwiazac
        grid[0][0] = 6;

        ISolver smartSolver = new SmartSolver();
        Assert.assertFalse(smartSolver.solve(grid));
        Assert.assertFalse(smartSolver.solve(grid2));
        Assert.assertFalse(smartSolver.solve(grid3));
        Assert.assertFalse(smartSolver.solve(grid4));
    }
}