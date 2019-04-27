package pl.sudokusolver.recognizerlib;

import pl.sudokusolver.recognizerlib.ml.ANN;
import pl.sudokusolver.recognizerlib.ml.IRecognizer;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuDetector;
import pl.sudokusolver.recognizerlib.sudokurecognizers.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.sudokurecognizers.Grid;

public class test {

    public static void main(String[] args) {
        System.out.println("Rec Lib Test");

        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");

        IRecognizer ann = new ANN("RecognizerLib/ann.xml");

        SudokuDetector sudokuDetector = new SudokuDetector(ann, new DigitBoxByteSum());
        Grid grid = new Grid();
        grid.imgToSudokuGrid("../Data/sudoku2.jpg");
        grid.cleanLines();
        sudokuDetector.getSudokuFromGrid(grid).printSudoku();

    }
}
