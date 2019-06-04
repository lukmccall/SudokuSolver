package pl.sudokusolver.recognizerlib.ocr;

/**
 * Interfejs pozwalający dostać się do modelu z opencv.
 */
public interface IRowModel {
    /**
     * @return model z opencv w postaci Object
     */
    Object getMl();
}
