package pl.sudokusolver.server.data;

public class OkResponse {
    public int status;
    public int[][] sudoku;

    public OkResponse(int status, int[][] sudoku) {
        this.status = status;
        this.sudoku = sudoku;
    }
}
