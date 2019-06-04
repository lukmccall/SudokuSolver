package pl.sudokusolver.server.bean;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;
import pl.sudokusolver.recognizerlib.ocr.tesseract.TesseractSimple;
import pl.sudokusolver.recognizerlib.ocr.tesseract.TesseractStrictMode;

import javax.annotation.PostConstruct;

public class DigitRecognizer {
    private String openCVUrl;
    private IRecognizer svm;
    private IRecognizer ann;
    private IRecognizer tesseractSimple;
    private IRecognizer tesseractStrict;
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

        this.svm = new SVM("..\\..\\Data\\svm.xml");
        this.ann = new SVM("..\\..\\Data\\ann.xml");
        this.tesseractSimple = new TesseractSimple();
        this.tesseractStrict = new TesseractStrictMode();
    }

    public IRecognizer getRecognizer(String type, boolean strictMode){
        if(type.equals("SVM")) return this.getSVM();
        if(type.equals("ANN")) return this.getANN();
        if(type.equals("TESSERACT") && !strictMode) return this.getTesseractSimple();
        if(type.equals("TESSERACT") && strictMode) return this.getTesseractStrict();
        return this.getSVM();
    }

    public IRecognizer getSVM() {
        return this.svm;
    }

    public IRecognizer getANN() {
        return this.ann;
    }

    public IRecognizer getTesseractSimple() {
        return this.tesseractSimple;
    }

    public IRecognizer getTesseractStrict() {
        return this.tesseractStrict;
    }

}
