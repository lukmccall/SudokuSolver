package pl.sudokusolver.server.models;

public class GridModel {
    public int status;
    public int[][] sudoku;

    public GridModel(int status, int[][] sudoku) {
        this.status = status;
        this.sudoku = sudoku;
    }
}
