package pl.sudokusolver.recognizerlib.ocr.ml.config;

import org.opencv.ml.ANN_MLP;

public interface annConfig {
    void config(ANN_MLP ann);
}
