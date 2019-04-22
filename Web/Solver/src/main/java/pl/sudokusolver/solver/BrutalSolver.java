package pl.sudokusolver.solver;

import javafx.util.Pair;
import pl.sudokusolver.solver.utility.Utility;

public class BrutalSolver implements ISolver {
    @Override
    public boolean solve(int[][] grid) {
        Pair<Integer, Integer> location;
        if ((location =Utility.getUnassignedLocation(grid)).getKey() == 9)return true;

        // todo: Make own pair with better names
        int row = location.getKey();
        int col = location.getValue();

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
