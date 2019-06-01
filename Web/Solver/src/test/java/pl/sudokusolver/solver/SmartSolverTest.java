package pl.sudokusolver.solver;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.sudokusolver.solver.utility.Utility;

import static org.junit.jupiter.api.Assertions.*;

class SmartSolverTest {

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
}