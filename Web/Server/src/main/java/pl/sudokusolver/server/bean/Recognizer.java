package pl.sudokusolver.server.bean;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.CleanLinesFilter;
import pl.sudokusolver.recognizerlib.filters.DisplayHelper;
import pl.sudokusolver.recognizerlib.ocr.ml.ANN;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.ocr.ml.PlaceTester;
import pl.sudokusolver.recognizerlib.sudoku.SudokuExtractor;

import javax.annotation.PostConstruct;
import java.util.Arrays;

public class Recognizer {
    private String openCVUrl;
    private IRecognizer recognizer;
    @Autowired
    private Logger LOGGER;
    public Recognizer(String openCVUrl){
        this.openCVUrl = openCVUrl;
    }

    @PostConstruct
    public void init(){
        LOGGER.info("Loading openCV from " + this.openCVUrl);
        Init.init(this.openCVUrl);
        this.recognizer = new ANN("../RecognizerLib/ann.xml");
    }

    public IRecognizer getRecognizer() {
        return this.recognizer;
    }
}
