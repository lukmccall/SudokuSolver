package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import static org.opencv.imgproc.Imgproc.*;

/**
 * This filter apply <code>GaussianBlur</code> and <code>adaptiveThreshold</code>.
 * <p>
 *     <b>Warning!</b><br>
 *     Matrix should be of type CV_8UC1 or any other type with one color channel.
 * </p>
 */
public class BlurFilter implements IFilter {
    /**
     * blur size
     */
    private int size;

    /**
     * adaptiveThreshold block size
     */
    private int blockSize;

    /**
     * const c using in adaptiveThreshold formula
     */
    private int c;

    /**
     * Creates object with default parameters.<br>
     * size = 5, blockSize = 19, c = 3.
     */
    public BlurFilter(){
        size = 5;
        blockSize = 19;
        c = 3;
    }

    /**
     * More information you can get from <a href="https://docs.opencv.org/4.0.1/d4/d86/group__imgproc__filter.html#gaabe8c836e97159a9193fb0b11ac52cf1">GaussianBlur</a> oraz <a href="https://docs.opencv.org/4.0.1/d7/d1b/group__imgproc__misc.html#ga72b913f352e4a1b1b397736707afcde3">adaptiveThreshold</a>
     * @param size size of gaussian blur
     * @param blockSize block size for <code>adaptiveThreshold</code> formula.
     * @param c const c used int <code>adaptiveThreshold</code> formula.
     */
    public BlurFilter(int size, int blockSize, int c) {
        if(size < 0 || blockSize < 2 || c < 0 || size % 2 != 1) throw new IllegalArgumentException();
        this.size = size;
        this.blockSize = blockSize;
        this.c = c;
    }

    @Override
    public void apply(Mat input) {
        GaussianBlur(input, input, new Size(size, size), 0);
        adaptiveThreshold(input, input, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY_INV, blockSize, c);
    }
}
