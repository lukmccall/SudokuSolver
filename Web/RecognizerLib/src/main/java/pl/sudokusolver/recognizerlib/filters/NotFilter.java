package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

import static org.opencv.core.Core.bitwise_not;

/**
 * Filter wykonujący operację negacji na macierzy
 */
public class NotFilter implements IFilter {
    @Override
    public void apply(Mat input) {
        bitwise_not(input, input);
    }
}
