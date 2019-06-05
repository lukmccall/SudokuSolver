package pl.sudokusolver.recognizerlib.ocr.tesseract;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.awt.image.BufferedImage;

/**
 * Opakowanie w formie singletonu zewnętrznego ocra - <a href="https://github.com/nguyenq/tess4j">Tesseract</a>.
 */
public class TesseractSingletonWrapper {
    /**
     * Instancja ocra
     */
    public final static Tesseract tesseract;

    static {
        tesseract = new Tesseract();
        tesseract.setDatapath(Utility.getTessdata());
        tesseract.setTessVariable("tessedit_char_whitelist", "123456789");
    }

    private TesseractSingletonWrapper() {}

}
