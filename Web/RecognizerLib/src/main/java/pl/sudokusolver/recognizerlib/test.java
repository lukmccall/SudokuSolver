package pl.sudokusolver.recognizerlib;

import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.ocr.ml.ANN;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuExtractor;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.extractors.grid.GridExtractor;

public class test {

    public static void main(String[] args) throws NotFoundSudokuExceptions {
        System.out.println("Rec Lib Test");

        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");

        IRecognizer ann = new ANN("RecognizerLib/ann.xml");

        SudokuExtractor sudokuExtractor = new SudokuExtractor(ann, new DigitBoxByteSum());
        GridExtractor gridExtractor = new GridExtractor();
        gridExtractor.imgToSudokuGrid("../Data/sudoku2.jpg");
        sudokuExtractor.getSudokuFromGrid(gridExtractor).printSudoku();

    }
}
