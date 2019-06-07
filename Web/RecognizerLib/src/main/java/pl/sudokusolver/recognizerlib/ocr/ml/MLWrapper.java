package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

/**
 * Abstract wrapper for IRecognizer interface. It's used for methods shearing.
 */
public abstract class MLWrapper implements IRecognizer {
    /**
     * sample size
     */
    protected short sampleSize;

    /**
     * @return sample size
     */
    public short getSampleSize() {
        return sampleSize;
    }

    /**
     * @param img matrix with digit
     * @return matrix which is centered and de-skewed.
     */
    protected Mat applyDigitFilter(Mat img){
        Mat center = ImageProcessing.center(img,sampleSize);
        Mat result = ImageProcessing.deskew(center,sampleSize);
        center.release();
        return result;
    }
}
