package pl.sudokusolver.recognizerlib;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategyManualTest;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategyManualTest;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategyManualTest;
import pl.sudokusolver.recognizerlib.filters.CleanLinesFilterManualTest;
import pl.sudokusolver.recognizerlib.ocr.RecognizerDumpManualTest;

import java.io.IOException;

@ExtendWith({_INIT_.class})
public class DumpManualTest {

    @Test
    @Ignore
    void dumpTest() throws IOException, DigitExtractionFailedException {
        new DefaultGridExtractStrategyManualTest().saveCuttedGrids();
        new CleanLinesFilterManualTest().clean();
        new SizeCellsExtractStrategyManualTest().extract();
        new FastDigitExtractStrategyManualTest().extract();
        new RecognizerDumpManualTest().SVMfromDump();
        new RecognizerDumpManualTest().ANNfromDump();
    }
}
