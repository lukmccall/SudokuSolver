package pl.sudokusolver.recognizerlib;

import pl.sudokusolver.recognizerlib.ml.ANN;
import pl.sudokusolver.recognizerlib.ml.IRecognizer;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuDetector;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.sudokurecognizers.GridImg;

public class test {

    public static void main(String[] args) {
        System.out.println("Rec Lib Test");

        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");

        IRecognizer ann = new ANN("RecognizerLib/ann.xml");

        SudokuDetector sudokuDetector = new SudokuDetector(ann, new DigitBoxByteSum());
        GridImg gridImg = new GridImg();
        gridImg.imgToSudokuGrid("../Data/sudoku2.jpg");
        sudokuDetector.getSudokuFromGrid(gridImg).printSudoku();

    }
}
