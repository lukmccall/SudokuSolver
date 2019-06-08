package pl.sudokusolver.solver;

/**
 * Abstract solving algorithm representation.
 */
public interface ISolver {
    /**
     * @param sudoku two dimension array which represent sudoku grid.
     * @return <code>true</code> if alg found solution, otherwise return <code>false</code>.
     */
    boolean solve(int[][] sudoku);
}
