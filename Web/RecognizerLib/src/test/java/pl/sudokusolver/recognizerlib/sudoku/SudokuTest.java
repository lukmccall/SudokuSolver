package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.jupiter.api.Test;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.ml.ANN;
import pl.sudokusolver.recognizerlib.ml.IRecognizer;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuDetector;
import pl.sudokusolver.recognizerlib.sudokurecognizers.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.sudokurecognizers.Grid;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuTest {
    @Test
    void sudokuOnCreateHaveEmptyGrid() {
        Sudoku sudoku = new Sudoku();
        for(short x = 0; x < 9; x++)
            for (short y = 0; y < 9; y++)
                assertEquals(0, sudoku.getDigit(x,y));

    }

    @Test
    void simpleSudokuRec(){
        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");

        IRecognizer ann = new ANN("ann.xml");

        SudokuDetector sudokuDetector = new SudokuDetector(ann, new DigitBoxByteSum());
        Grid grid = new Grid();
        grid.imgToSudokuGrid("../../Data/sudoku2.jpg");
        grid.cleanLines();
        Sudoku s = sudokuDetector.getSudokuFromGrid(grid);
        s.printSudoku();
        assertEquals(3,s.getDigit(0,0));

    }

}
