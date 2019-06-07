package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

/**
 * This filter resize, using proportion, image to be smaller then max size.<br>
 */
public class MaxResizeFilter implements IFilter {
    /**
     * max size of image
     */
    private Size maxSize;

    /**
     * Create object with default values.<br>
     * maxSize = new Size(1000,1000)
     */
    public MaxResizeFilter() {
        this.maxSize = new Size(1000,1000);
    }

    /**
     * @param maxSize max size of output img
     */
    public MaxResizeFilter(Size maxSize) {
        if(maxSize.width <= 0 || maxSize.height <= 0) throw new IllegalArgumentException();
        this.maxSize = maxSize;
    }

    @Override
    public void apply(Mat input) {
        ImageProcessing.resizeToMaxSize(input, maxSize);
    }
}
