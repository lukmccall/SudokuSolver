package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.extractors.cells.LineCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.ByteSumDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.ContoursDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.*;
import pl.sudokusolver.recognizerlib.ocr.ml.PlaceTester;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({_INIT_.class})
class SudokuExtractorTest {

    @Test
    void sudokuExtraction() throws IOException, NotFoundSudokuExceptions {
        List<String> images = _TestUtility_.getAllImages();
        SudokuExtractor sudokuExtractor1 = new SudokuExtractor(
                new DefaultGridExtractStrategy(),
                new LineCellsExtractStrategy(),
                new ContoursDigitExtractStrategy(),
                new PlaceTester(),
                Arrays.asList(new ResizeFilter(600)),
                Arrays.asList(new ToGrayFilter(),new BlurFilter(), new DisplayHelper()),
                null
        );
        SudokuExtractor sudokuExtractor2 = new SudokuExtractor(
                new DefaultGridExtractStrategy(),
                new SizeCellsExtractStrategy(),
                new ByteSumDigitExtractStrategy(),
                new PlaceTester(),
                Arrays.asList(new ResizeFilter(600)),
                Arrays.asList(new CleanLinesFilter() , new DisplayHelper()),
                null
        );

        for(String img : images){
            sudokuExtractor1.extract(img).printSudoku();
            sudokuExtractor2.extract(img).printSudoku();

        }
    }

}