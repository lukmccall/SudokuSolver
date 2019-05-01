package pl.sudokusolver.recognizerlib.ocr.tesseract;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.Utility;

public class TesseractWrapper implements IRecognizer {
    public static Tesseract tesseract;
    static {
        tesseract = new Tesseract();
        tesseract.setTessVariable("tessedit_char_whitelist", "123456789");
    }
    @Override
    public int detect(Mat img) {
        String text = "";
        try {
            text = tesseract.doOCR(Utility.matToBufferedImage(img));
        } catch (TesseractException e) {
            e.printStackTrace();
            return 0;
        }

        return Integer.parseInt(text);
    }
}
