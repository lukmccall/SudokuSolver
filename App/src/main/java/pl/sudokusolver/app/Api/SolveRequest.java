package pl.sudokusolver.app.Api;

public class SolveRequest {
    public int[][] grid;

    public SolveRequest(int[][] grid){
        this.grid = grid;
    }
}
