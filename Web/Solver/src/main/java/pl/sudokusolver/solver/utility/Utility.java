package pl.sudokusolver.solver.utility;


/**
 * Utility class.
 */
public class Utility {
    /**
     * Check if number was used in row.
     * @param grid grid.
     * @param row row.
     * @param num number.
     * @return <code>true</code> if number was used in row, otherwise <code>false</code>.
     */
    public static boolean usedInRow(int grid[][], int row, int num) {
        outOfBound(row, 0);
        for (int col = 0; col < 9; col++)
            if (grid[row][col] == num) return true;
        return false;
    }

    /**
     * Check if number was used in col.
     * @param grid grid.
     * @param col col.
     * @param num number.
     * @return <code>true</code> if number was used in col, otherwise <code>false</code>.
     */
    public static boolean usedInCol(int grid[][], int col, int num) {
        outOfBound(col, 0);
        for (int row = 0; row < 9; row++)
            if (grid[row][col] == num) return true;
        return false;
    }

    /**
     * Check if number was used in box (3 x 3).
     * @param grid grid.
     * @param startRow start row.
     * @param startCol start col.
     * @param num number.
     * @return <code>true</code> if number was used in box, otherwise <code>false</code>.
     */
    public static boolean  usedInBox(int grid[][], int startRow, int startCol, int num){
        outOfBound(startRow, startCol);
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (grid[row + startRow][col + startCol] == num) return true;
        return false;
    }

    /**
     * Check if you can put digit with given place.
     * @param grid grid.
     * @param row row.
     * @param col col.
     * @param num number.
     * @return <code>true</code> if you can put digit, otherwise <code>false</code>.
     */
    public static boolean canPlaceDigit(int grid[][], int row, int col, int num) {
        outOfBound(row, col);
        return !usedInRow(grid, row, num) &&
               !usedInCol(grid, col, num) &&
               !usedInBox(grid, row - row % 3, col - col % 3, num);
    }

    /**
     * @param grid grid
     * @return coordinates of place which is empty or (9 , 9) when grid is completed.
     */
    public static Pair<Integer, Integer> getUnassignedLocation(int grid[][]) {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                if (grid[row][col] == 0)
                    return new Pair<>(row,col);

        return new Pair<>(9,9);
    }

    /**
     * @param grid grid.
     * @return string from grid.
     */
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

    /**
     * If x or y is out of bound throw <code>IndexOutOfBoundsException</code>.
     * @param x x
     * @param y y
     */
    private static void outOfBound(int x, int y){
        if(x >= 9 || y >= 9) throw new IndexOutOfBoundsException();
        if(x < 0 || y < 0) throw new IndexOutOfBoundsException();
    }

}
