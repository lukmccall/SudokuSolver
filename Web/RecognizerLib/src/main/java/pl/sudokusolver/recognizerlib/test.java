package pl.sudokusolver.recognizerlib;

import pl.sudokusolver.recognizerlib.digitsrecognizers.ANN;
import pl.sudokusolver.recognizerlib.gridrecognizers.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.gridrecognizers.Grid;
import pl.sudokusolver.recognizerlib.gridrecognizers.SudokuDetector;

public class test {

    static {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println("Rec Lib Test");


        ANN ann = new ANN("RecognizerLib/ann.xml");

        SudokuDetector sudokuDetector = new SudokuDetector(ann, new DigitBoxByteSum());
        Grid grid = new Grid();
        grid.imgToSudokuGrid("../Data/sudoku2.jpg");
        grid.cleanLines();
        sudokuDetector.getSudokuFromGrid(grid).printSudoku();

    }
}
