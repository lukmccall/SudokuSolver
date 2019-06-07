package pl.sudokusolver.recognizerlib.ocr.ml.config;

/**
 * Config interface for svm.
 */
public interface svmConfig {
    /**
     * Using this you are able to create your own ann configuration.
     * @param svm svm model. You need to configure this model.
     */
    void config(org.opencv.ml.SVM svm);
}
