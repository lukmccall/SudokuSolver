package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import static org.opencv.imgproc.Imgproc.resize;

/**
 * Filter zmieniający rozmiar macierzy
 */
public class ResizeFilter implements IFilter{
    private Size size;

    /**
     * @param size wielkość macierzy wyjściowej
     */
    public ResizeFilter(Size size) {
        this.size = size;
    }

    /**
     * @param size wielkość macierzy wyjściowej (kwadrat: size x size)
     */
    public ResizeFilter(int size){
        this.size = new Size(size, size);
    }

    @Override
    public void apply(Mat input) {
        resize(input,input, size);
    }
}
