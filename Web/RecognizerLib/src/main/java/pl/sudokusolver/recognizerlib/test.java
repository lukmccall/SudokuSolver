package pl.sudokusolver.recognizerlib;

import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.extractors.cells.LineCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.ContoursDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.*;
import pl.sudokusolver.recognizerlib.ocr.ml.PlaceTester;
import pl.sudokusolver.recognizerlib.sudoku.SudokuExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class test {

    public static void main(String[] args) throws NotFoundSudokuExceptions {
        System.out.println("Rec Lib Test");

        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");

//        IRecognizer ann = new ANN("RecognizerLib/ann.xml");

        SudokuExtractor sudokuExtractor = new SudokuExtractor(
                new DefaultGridExtractStrategy(),
                new LineCellsExtractStrategy(),
                new ContoursDigitExtractStrategy(),
                new PlaceTester(),
                null,
                Arrays.asList( new ToGrayFilter(),new BlurFilter() , new DisplayHelper()),
                null

        );
        sudokuExtractor.extract("../Data/sudoku2.jpg").printSudoku();

    }
}
