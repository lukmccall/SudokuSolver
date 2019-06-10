package pl.sudokusolver.server.bean;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.ocr.ml.ANN;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;
import pl.sudokusolver.recognizerlib.ocr.tesseract.TesseractSimple;
import pl.sudokusolver.recognizerlib.ocr.tesseract.TesseractStrictMode;
import pl.sudokusolver.recognizerlib.ocr.tesseract.TesseractSingletonWrapper;
import pl.sudokusolver.recognizerlib.utility.ResourceManager;
import pl.sudokusolver.server.utility.Utility;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.jar.JarFile;

/**
 * Singleton bean for all ocr.
 */
public class DigitRecognizer {
    /**
     * path to opencv dll.
     */
    private String openCVUrl;

    /**
     * svm model.
     */
    private IRecognizer svm;

    /**
     * ann model.
     */
    private IRecognizer ann;

    /**
     * tesseract simple mode model.
     */
    private IRecognizer tesseractSimple;

    /**
     * tesseract strict mode model.
     */
    private IRecognizer tesseractStrict;


    /**
     * logger
     */
    @Autowired
    private Logger LOGGER;

    /**
     * @param openCVUrl path to openCV
     */
    public DigitRecognizer(String openCVUrl){
        this.openCVUrl = openCVUrl;
    }

    /**
     * Load openCV and all ocrs.
     */
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



        this.svm = new SVM(ResourceManager.extract("/svm.xml"));
        this.ann = new ANN(ResourceManager.extract("/ann.xml"));
        TesseractSingletonWrapper.tesseract.setDatapath(System.getProperty("user.dir")+"/tessdata/");
        this.tesseractSimple = new TesseractSimple();
        this.tesseractStrict = new TesseractStrictMode();
    }

    /**
     * @param type name of model.<br>
     *             You can chose one of:<br>
     *             <ul>
     *                  <li>SVM</li>
     *                  <li>ANN</li>
     *                  <li>TESSERACT (without strict mode)</li>
     *                  <li>TESSERACT (with strict mode)</li>
     *             </ul>
     * @param strictMode strict mode.
     * @return chosen model.
     */
    public IRecognizer getRecognizer(String type, boolean strictMode){
        if(type.equals("SVM")) return this.svm;
        if(type.equals("ANN")) return this.ann;
        if(type.equals("TESSERACT") && !strictMode) return this.tesseractSimple;
        if(type.equals("TESSERACT") && strictMode) return this.tesseractStrict;
        return this.svm;
    }
}
