package pl.sudokusolver.recognizerlib.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.utility.ImageProcessing;

public abstract class MLWrapper implements IRecognizer{
    protected short sampleSize;

    public short getSampleSize() {
        return sampleSize;
    }

    protected Mat applyFilter(Mat img){
        return ImageProcessing.deskew(ImageProcessing.center(img.clone(),sampleSize),sampleSize);
    }
}
