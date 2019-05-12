package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

public class MaxResizeFilter implements IFilter {
    private Size maxSize;

    public MaxResizeFilter() {
        this.maxSize = new Size(1000,1000);
    }

    public MaxResizeFilter(Size maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void apply(Mat input) {
        input = ImageProcessing.resizeToMaxSize(input, maxSize);
    }
}
