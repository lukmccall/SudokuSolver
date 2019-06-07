package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.CvException;

/**
 * Models which implements this interface are able to save and load their state.
 */
public interface ILoader {
    /**
     * Create model and then load its dump form file.
     * @param url absolute path to dump file.
     * @throws CvException if couldn't open file
     */
    void load(String url) throws CvException;

    /**
     * Save model's state. In opencv model's state is saved as xml file.
     * @param url path (with extension) where you want to save dump.
     */
    void dump(String url);
}
