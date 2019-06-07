package pl.sudokusolver.recognizerlib.ocr.ml.config;

import org.opencv.ml.ANN_MLP;

/**
 * Config interface for ann.
 */
public interface annConfig {
    /**
     * Using this you are able to create your own ann configuration.
     * @param ann ann model. You need to configure this model.
     */
    void config(ANN_MLP ann);
}
