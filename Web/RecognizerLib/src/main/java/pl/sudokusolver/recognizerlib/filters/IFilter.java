package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

/**
 * Interface for all filters.<br>
 * Filters are simple object. They are able to affect matrixs and transform them.<br>
 * All filters can change given matrix (have side effects)!<br>
 */
public interface IFilter {
    /**
     * Apply concrete filter to matrix.
     * @param input matrix with image. It will be changed!
     */
    void apply(Mat input);
}
