package pl.sudokusolver.recognizerlib;

import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.ml.ANN;
import pl.sudokusolver.recognizerlib.ml.IRecognizer;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuDetector;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.gridextractors.GridExtractor;

public class test {

    public static void main(String[] args) throws NotFoundSudokuExceptions {
        System.out.println("Rec Lib Test");

        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");

        IRecognizer ann = new ANN("RecognizerLib/ann.xml");

        SudokuDetector sudokuDetector = new SudokuDetector(ann, new DigitBoxByteSum());
        GridExtractor gridExtractor = new GridExtractor();
        gridExtractor.imgToSudokuGrid("../Data/sudoku2.jpg");
        sudokuDetector.getSudokuFromGrid(gridExtractor).printSudoku();

    }
}
