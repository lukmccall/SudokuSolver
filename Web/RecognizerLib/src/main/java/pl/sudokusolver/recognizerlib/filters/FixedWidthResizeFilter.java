package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import static org.opencv.imgproc.Imgproc.resize;

public class FixedWidthResizeFilter implements IFilter {
    private int width;

    public FixedWidthResizeFilter(){
        this.width = 1000;
    }

    public FixedWidthResizeFilter(int width){
        this.width = width;
    }

    @Override
    public void apply(Mat input) {
        double ratio = input.size().height/input.size().width;
        resize(input,input, new Size(width,width*ratio));
    }
}
