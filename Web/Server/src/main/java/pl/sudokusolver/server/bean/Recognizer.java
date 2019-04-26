package pl.sudokusolver.server.bean;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.digitsrecognizers.ANN;
import pl.sudokusolver.recognizerlib.digitsrecognizers.IRecognizer;
import pl.sudokusolver.recognizerlib.sudokurecognizers.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuDetector;

import javax.annotation.PostConstruct;

public class Recognizer {
    private String openCVUrl;
    private SudokuDetector sudokuDetector;
    @Autowired
    private Logger LOGGER;
    public Recognizer(String openCVUrl){
        this.openCVUrl = openCVUrl;
    }

    @PostConstruct
    public void init(){
        LOGGER.info("Loading openCV from " + this.openCVUrl);
        Init.init(this.openCVUrl);
        IRecognizer ann = new ANN("../RecognizerLib/ann.xml");
        sudokuDetector = new SudokuDetector(ann, new DigitBoxByteSum());
    }

    public SudokuDetector getDetector() {
        return sudokuDetector;
    }
}
