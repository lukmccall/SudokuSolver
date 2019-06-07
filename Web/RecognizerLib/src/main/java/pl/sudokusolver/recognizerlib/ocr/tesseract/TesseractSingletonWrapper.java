package pl.sudokusolver.recognizerlib.ocr.tesseract;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.awt.image.BufferedImage;

/**
 * Wrapper for external ocra - <a href="https://github.com/nguyenq/tess4j">Tesseract</a>.
 */
public class TesseractSingletonWrapper {
    /**
     * single instance of tesseract object.
     */
    public final static Tesseract tesseract;

    // init tesseract data and changing rec rules
    static {
        tesseract = new Tesseract();
        tesseract.setDatapath(Utility.getTessdata());
        tesseract.setTessVariable("tessedit_char_whitelist", "123456789");
    }

    /**
     * You shouldn't be able to create instance of this class.
     */
    private TesseractSingletonWrapper() {}

}
