package pl.sudokusolver.recognizerlib.ocr.tesseract;

import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

public class TesseractStrictMode implements IRecognizer {
    @Override
    public Pair<Integer, Double> recognize(Mat img) throws DigitExtractionFailedException {
        String text;
        try {
            text = TesseractSingletonWrapper.tesseract.doOCR(Utility.matToBufferedImage(img));
        } catch (TesseractException e) {
            throw new DigitExtractionFailedException("Can't extract digit", e);
        }
        text = text.replaceAll("[^0-9]", "");
        if(text.isEmpty()) throw new DigitExtractionFailedException();
        int g = Integer.parseInt(text)%10;
        return new Pair<>(g, 1.0);
    }
}
