package pl.sudokusolver.server.bean;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.ml.ANN;
import pl.sudokusolver.recognizerlib.ml.IRecognizer;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuExtractor;

import javax.annotation.PostConstruct;

public class Recognizer {
    private String openCVUrl;
    private SudokuExtractor sudokuExtractor;
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
        sudokuExtractor = new SudokuExtractor(ann, new DigitBoxByteSum());
    }

    public SudokuExtractor getDetector() {
        return sudokuExtractor;
    }
}
