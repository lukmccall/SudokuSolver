package pl.sudokusolver.sudokurecognizerlib.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuTest {
    @Test
    void sudokuOnCreateHaveGrid() {
        Sudoku sudoku = new Sudoku();
        for(short x = 0; x < 9; x++)
            for (short y = 0; y < 9; y++)
                assertEquals(0, sudoku.getDigit(x,y));

    }

}
