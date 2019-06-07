package pl.sudokusolver.recognizerlib.ocr;

/**
 * If class implement this interface then you can get bare model which is using by this class.
 */
public interface IRowModel {
    /**
     * @return opencv model
     */
    Object getMl();
}
