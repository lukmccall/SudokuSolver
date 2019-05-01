package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

public abstract class MLWrapper implements IRecognizer {
    protected short sampleSize;

    public short getSampleSize() {
        return sampleSize;
    }

    protected Mat applyFilter(Mat img){
        return ImageProcessing.deskew(ImageProcessing.center(img.clone(),sampleSize),sampleSize);
    }
}
