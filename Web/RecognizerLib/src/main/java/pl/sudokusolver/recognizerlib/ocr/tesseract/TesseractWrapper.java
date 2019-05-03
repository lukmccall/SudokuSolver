package pl.sudokusolver.recognizerlib.ocr.tesseract;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

public class TesseractWrapper implements IRecognizer {
    public static Tesseract tesseract;
    static {
        tesseract = new Tesseract();
        tesseract.setTessVariable("tessedit_char_whitelist", "123456789");
    }
    @Override
    public Pair<Integer, Double> recognize(Mat img) {
        String text;
        try {
            text = tesseract.doOCR(Utility.matToBufferedImage(img));
        } catch (TesseractException e) {
            e.printStackTrace();
            return new Pair<>(0, 0.0);
        }

        return new Pair<>(Integer.parseInt(text), 1.0);
    }
}
