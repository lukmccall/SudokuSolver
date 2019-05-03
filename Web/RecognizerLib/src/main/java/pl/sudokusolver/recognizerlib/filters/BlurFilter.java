package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY_INV;

public class BlurFilter implements IFilter {
    private int size;
    private int blockSize;
    private int c;

    public BlurFilter(){
        size = 5;
        blockSize = 19;
        c = 3;
    }

    public BlurFilter(int size, int blockSize, int c) {
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
