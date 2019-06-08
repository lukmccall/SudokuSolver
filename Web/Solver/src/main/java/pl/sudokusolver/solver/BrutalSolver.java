package pl.sudokusolver.solver;


import pl.sudokusolver.solver.utility.Pair;
import pl.sudokusolver.solver.utility.Utility;

/**
 * Now unused.
 * Implementation of brutal sudoku solver algorithm
 */
public class BrutalSolver implements ISolver {
    @Override
    public boolean solve(final int[][] grid) {
        Pair<Integer, Integer> location;
        if ((location = Utility.getUnassignedLocation(grid)).getFirst() == 9)
            return true;

        int row = location.getFirst();
        int col = location.getSecond();

        for (int num = 1; num <= 9; num++){
            if (Utility.canPlaceDigit(grid, row, col, num)){
                grid[row][col] = num;
                if (solve(grid)) return true;
                grid[row][col] = 0;
            }
        }
        return false;
    }
}
