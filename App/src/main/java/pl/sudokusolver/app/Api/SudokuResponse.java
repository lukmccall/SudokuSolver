package pl.sudokusolver.app.Api;

public class SudokuResponse {
    public int status;
    public int[][] sudoku;

    public SudokuResponse(int status, int[][] sudoku) {
        this.status = status;
        this.sudoku = sudoku;
    }
}
