package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;

/**
 * This filter convert from colorful to black and white image.
 */
public class ToGrayFilter implements IFilter {
    @Override
    public void apply(Mat input) {
        // todo: bug report
        if(input.channels() != 1)
            cvtColor(input, input, COLOR_BGR2GRAY);
    }
}
