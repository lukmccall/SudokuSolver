package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

/**
 * Opakowanie interfejsu IRecognizer.
 * <p>
 *     Jego celem jest zapamiętanie rozmiaru próbki oraz ujednolicenie filtra stosowanego na cyfrę do rozpoznania.
 * </p>
 */
public abstract class MLWrapper implements IRecognizer {
    /**
     * Rozmiar próbki
     */
    protected short sampleSize;

    /**
     * @return rozmiar próbki
     */
    public short getSampleSize() {
        return sampleSize;
    }

    /**
     * @param img macierz z cyfrą
     * @return macierz z cyfrą po zastosowaniu filtrów
     */
    protected Mat applyDigitFilter(Mat img){
        return ImageProcessing.center(img.clone(),sampleSize);
//        return ImageProcessing.deskew(ImageProcessing.center(img.clone(),sampleSize),sampleSize);
//        return ImageProcessing.deskew(img.clone(),sampleSize);
    }
}
