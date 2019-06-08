package pl.sudokusolver.server.models;

/**
 * Class used to generated response in json.
 */
public class GridModel {
    /**
     * response status.<br>
     *     1 - everything is ok<br>
     *     0 - something goes wrong
     */
    public int status;

    /**
     * sudoku grid.
     */
    public int[][] sudoku;

    /**
     * Crete object form given parameters.
     * @param status status
     * @param sudoku grid
     */
    public GridModel(int status, int[][] sudoku) {
        this.status = status;
        this.sudoku = sudoku;
    }
}
