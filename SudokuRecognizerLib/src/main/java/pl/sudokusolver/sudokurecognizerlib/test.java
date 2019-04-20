package pl.sudokusolver.sudokurecognizerlib;

import pl.sudokusolver.sudokurecognizerlib.dataproviders.DataType;
import pl.sudokusolver.sudokurecognizerlib.dataproviders.IData;
import pl.sudokusolver.sudokurecognizerlib.dataproviders.MNISTReader;
import pl.sudokusolver.sudokurecognizerlib.digitsrecognizers.ANN;
import pl.sudokusolver.sudokurecognizerlib.gridrecognizers.DigitBoxByteSum;
import pl.sudokusolver.sudokurecognizerlib.gridrecognizers.Grid;
import pl.sudokusolver.sudokurecognizerlib.gridrecognizers.IDigitBox;
import pl.sudokusolver.sudokurecognizerlib.gridrecognizers.SudokuDetector;

public class test {

    static {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println("Rec Lib Test");


        ANN ann = new ANN("ann.xml");

        SudokuDetector sudokuDetector = new SudokuDetector(ann, new DigitBoxByteSum());
        Grid grid = new Grid();
        grid.imgToSudokuGrid("../Data/sudoku2.jpg");
        grid.cleanLines();
        sudokuDetector.getSudokuFromGrid(grid).printSudoku();

    }
}
