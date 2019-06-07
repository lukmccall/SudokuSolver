package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import static org.opencv.imgproc.Imgproc.resize;

/**
 * This filter resize matrix. Output matrix size depends on given parameters.
 */
public class ResizeFilter implements IFilter{
    /**
     * output size
     */
    private Size size;

    /**
     * @param size size of output matrix
     */
    public ResizeFilter(Size size) {
        if(size.height <= 0 || size.width <= 0) throw new IllegalArgumentException();
        this.size = size;
    }

    /**
     * @param size size of output matrix (it is rectangle <code>size*size</code>)
     */
    public ResizeFilter(int size){
        if(size <= 0) throw new IllegalArgumentException();
        this.size = new Size(size, size);
    }

    @Override
    public void apply(Mat input) {
        resize(input,input, size);
    }
}
