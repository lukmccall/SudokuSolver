package pl.sudokusolver.solver.utility;

import javafx.util.Pair;

public class Utility {
    public static boolean usedInRow(int grid[][], int row, int num) {
        outOfBound(row, 0);
        for (int col = 0; col < 9; col++)
            if (grid[row][col] == num) return true;
        return false;
    }

    public static boolean usedInCol(int grid[][], int col, int num) {
        outOfBound(col, 0);
        for (int row = 0; row < 9; row++)
            if (grid[row][col] == num) return true;
        return false;
    }

    public static boolean  usedInBox(int grid[][], int startRow, int startCol, int num){
        outOfBound(startRow, startCol);
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (grid[row + startRow][col + startCol] == num) return true;
        return false;
    }

    public static boolean canPlaceDigit(int grid[][], int row, int col, int num) {
        outOfBound(row, col);
        return !usedInRow(grid, row, num) &&
               !usedInCol(grid, col, num) &&
               !usedInBox(grid, row - row % 3, col - col % 3, num);
    }

    public static Pair<Integer, Integer> getUnassignedLocation(int grid[][]) {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                if (grid[row][col] == 0)
                    return new Pair<>(row,col);

        return new Pair<>(9,9);
    }

    public static String gridToString(int grid[][]){
        StringBuilder ss = new StringBuilder();
        for(int i = 0; i < 9; i++){
            ss.append("[");
            for (int j = 0; j < 8; j++)
                ss.append(grid[i][j]).append(", ");
            ss.append(grid[i][8]).append("]").append(System.getProperty("line.separator"));
        }
        return ss.toString();
    }

    private static void outOfBound(int x, int y){
        if(x >= 9 || y >= 9) throw new IndexOutOfBoundsException();
        if(x < 0 || y < 0) throw new IndexOutOfBoundsException();
    }

}
