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
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.*;
import pl.sudokusolver.recognizerlib.ocr.ml.PlaceTester;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith({_INIT_.class})
class SudokuExtractorTest {

    @Test
    void sudokuExtraction() throws IOException, NotFoundSudokuExceptions {
        List<String> images = _TestUtility_.getAllImages().subList(6,7);
        SudokuExtractor sudokuExtractor1 = new SudokuExtractor(
                new DefaultGridExtractStrategy(),
                new LineCellsExtractStrategy(),
                new ContoursDigitExtractStrategy(),
                new PlaceTester(),
                null,
                Arrays.asList(new ToGrayFilter(),new BlurFilter(), new DisplayHelper()),
                null
        );
        SudokuExtractor sudokuExtractor2 = new SudokuExtractor(
                new DefaultGridExtractStrategy(),
                new SizeCellsExtractStrategy(),
                new FastDigitExtractStrategy(),
                new PlaceTester(),
                null,
                Arrays.asList(new CleanLinesFilter() , new DisplayHelper()),
                null
        );

        for(String img : images){
            sudokuExtractor1.extract(img).printSudoku();
            sudokuExtractor2.extract(img).printSudoku();

        }
    }

}