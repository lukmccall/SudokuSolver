package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

import static org.opencv.imgproc.Imgproc.*;

/**
 * Filter służący do nakładanie rozmycia
 * <p>
 *     <b>Uwaga</b><br>
 *     Zdjęcie wejściowe musi być czarno-białe (np. CV_8UC1)
 * </p>
 */
public class MedianBlur implements IFilter {
    private int size;
    private int blockSize;
    private int c;

    /**
     * Domyśle parametry filtra
     */
    public MedianBlur(){
        size = 5;
        blockSize = 19;
        c = 3;
    }

    /**
     * Dodatkowe infomacje <a href="https://docs.opencv.org/4.0.1/d4/d86/group__imgproc__filter.html#gaabe8c836e97159a9193fb0b11ac52cf1">GaussianBlur</a> oraz <a href="https://docs.opencv.org/4.0.1/d7/d1b/group__imgproc__misc.html#ga72b913f352e4a1b1b397736707afcde3">adaptiveThreshold</a>
     * @param size wielkość rozmycia
     * @param blockSize wielkość bloku, używanego do obliczania różnicy po między pixelami
     * @param c stała odejmowana od różnicy
     */
    public MedianBlur(int size, int blockSize, int c) {
        if(size < 0 || blockSize < 2 || c < 0 || size % 2 != 1) throw new IllegalArgumentException();
        this.size = size;
        this.blockSize = blockSize;
        this.c = c;
    }

    @Override
    public void apply(Mat input) {
        medianBlur(input,input, size);
        adaptiveThreshold(input, input, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY_INV, blockSize, c);
    }
}
