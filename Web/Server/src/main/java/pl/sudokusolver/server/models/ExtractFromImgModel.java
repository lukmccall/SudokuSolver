package pl.sudokusolver.server.models;

import pl.sudokusolver.recognizerlib.sudoku.Sudoku;

public class ExtractFromImgModel {
    public int status;
    public int[][] sudoku;

    public ExtractFromImgModel(int status, int[][] sudoku) {
        this.status = status;
        this.sudoku = sudoku;
    }
}
