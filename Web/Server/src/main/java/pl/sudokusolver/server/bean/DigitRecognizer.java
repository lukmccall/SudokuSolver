package pl.sudokusolver.server.bean;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.ocr.ml.ANN;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;

import javax.annotation.PostConstruct;

public class DigitRecognizer {
    private String openCVUrl;
    private IRecognizer recognizer;
    @Autowired
    private Logger LOGGER;
    public DigitRecognizer(String openCVUrl){
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
