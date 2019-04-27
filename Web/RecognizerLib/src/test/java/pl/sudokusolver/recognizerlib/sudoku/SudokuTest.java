package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.ml.ANN;
import pl.sudokusolver.recognizerlib.ml.IRecognizer;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuDetector;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.sudokurecognizers.GridImg;

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

    @Test
    void simpleSudokuRec(){
        IRecognizer ann = new ANN("ann.xml");

        SudokuDetector sudokuDetector = new SudokuDetector(ann, new DigitBoxByteSum());
        GridImg gridImg = new GridImg();
        gridImg.imgToSudokuGrid("../../Data/sudoku2.jpg");
        Sudoku s = sudokuDetector.getSudokuFromGrid(gridImg);
        s.printSudoku();
        assertEquals(3,s.getDigit(0,0));

    }

}
