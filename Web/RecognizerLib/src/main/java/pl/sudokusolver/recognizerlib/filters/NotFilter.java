package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

import static org.opencv.core.Core.bitwise_not;

/**
 * This filter apply bitwise not operation on matrix.
 */
public class NotFilter implements IFilter {
    @Override
    public void apply(Mat input) {
        bitwise_not(input, input);
    }
}
