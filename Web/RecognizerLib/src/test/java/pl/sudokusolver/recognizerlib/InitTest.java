package pl.sudokusolver.recognizerlib;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategyTest;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategyTest;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategyTest;
import pl.sudokusolver.recognizerlib.filters.CleanLinesFilterTest;
import pl.sudokusolver.recognizerlib.ocr.IRecognizerTest;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;
import pl.sudokusolver.recognizerlib.ocr.ml.SVMTest;

import java.io.IOException;

@ExtendWith({_INIT_.class})
class InitTest {
    @Test
    void checkMainResource(){
        Assert.assertNotNull("Resources should have svm file", Init.class.getResource("/svm.xml"));
        Assert.assertNotNull("Resources should have ann file", Init.class.getResource("/ann.xml"));
        Assert.assertNotNull("Resources should have tasseract file", Init.class.getResource("/tessdata/"));
        Assert.assertNotNull("Resources should have tasseract file", Init.class.getResource("/tessdata/eng.traineddata"));
     }

    // for developers
    @Test
    @Ignore
    void dump() throws IOException, DigitExtractionFailedException {
        DefaultGridExtractStrategyTest grid = new DefaultGridExtractStrategyTest();
        CleanLinesFilterTest clean = new CleanLinesFilterTest();
        SizeCellsExtractStrategyTest cells = new SizeCellsExtractStrategyTest();
        FastDigitExtractStrategyTest digit = new FastDigitExtractStrategyTest();
        IRecognizerTest iRecognizerTest = new IRecognizerTest();

        grid.saveCuttedGrids();
        clean.clean();
        cells.extract();
        digit.extract();
        iRecognizerTest.SVMfromDump();

    }

}