package pl.sudokusolver.server.bean;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;

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
        LOGGER.info("*********** OpenCV 4.0.1 ***********");
        if(this.openCVUrl.isEmpty()){
            LOGGER.info("Loading openCV from default path");
            LOGGER.info( org.opencv.core.Core.NATIVE_LIBRARY_NAME);
            Init.init();

        } else {
            LOGGER.info("Loading openCV from " + this.openCVUrl);
            Init.init(this.openCVUrl);
        }

        this.recognizer = new SVM("..\\..\\Data\\svm.xml");
    }

    public IRecognizer getRecognizer() {
        return this.recognizer;
    }
}
