package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import static org.opencv.imgproc.Imgproc.resize;

/**
 * This filter resize matrix. Output matrix width is fixed and depend on given parameter.<br>
 * Height is rescale using simple proportion to not change image aspect ration.
 */
public class FixedWidthResizeFilter implements IFilter {
    /**
     * output with.
     */
    private int width;

    /**
     * Crete object with default values (width = 1000).
     */
    public FixedWidthResizeFilter(){
        this.width = 1000;
    }

    /**
     * @param width value of matrix width after resizing.
     */
    public FixedWidthResizeFilter(int width){
        this.width = width;
    }

    @Override
    public void apply(Mat input) {
        double ratio = input.size().height/input.size().width;
        resize(input,input, new Size(width,width*ratio));
    }
}
