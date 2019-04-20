package pl.sudokusolver.sudokurecognizerlib.sudoku;

import pl.sudokusolver.sudokurecognizerlib.gridrecognizers.Grid;

public class Sudoku {
    private short grid[][];

    public short getDigit(short x, short y) {
        return grid[x][y];
    }

    public void setDigir(short digit, short x, short y) {
        this.grid[x][y] = digit;
    }
}
