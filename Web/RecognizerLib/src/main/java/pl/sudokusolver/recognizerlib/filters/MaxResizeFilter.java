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
        if(maxSize.width <= 0 || maxSize.height <= 0) throw new IllegalArgumentException();
        this.maxSize = maxSize;
    }

    @Override
    public void apply(Mat input) {
        ImageProcessing.resizeToMaxSize(input, maxSize);
    }
}
