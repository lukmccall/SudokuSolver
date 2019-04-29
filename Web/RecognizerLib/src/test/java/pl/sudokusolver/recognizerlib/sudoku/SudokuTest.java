package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({_INIT_.class})
public class SudokuTest {


    @Test
    void sudokuOnCreateHaveEmptyGrid() {
        Sudoku sudoku = new Sudoku();
        for(short x = 0; x < 9; x++)
            for (short y = 0; y < 9; y++)
                assertEquals(0, sudoku.getDigit(x,y));

    }

}
