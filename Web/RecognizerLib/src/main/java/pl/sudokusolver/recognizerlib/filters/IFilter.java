package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

public interface IFilter {
    void apply(Mat input);
}
