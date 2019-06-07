package pl.sudokusolver.recognizerlib.data;

/**
 * Types used to define format of data.<br>
 * This describe how labels look like.<br>
 * You can use:
 * <ul>
 *     <li>{@link #Simple}</li>
 *     <li>{@link #SimpleSVM}</li>
 *     <li>{@link #Complex}</li>
 * </ul>
 */
public enum DataType {
    /**
     * <p>Labels are stored in matrix which have only once column</p>
     * <p>This matrix is type of CV_32FC1.</p>
     */
    Simple,

    /**
     * <p>Labels are stored in matrix which have only once column</p>
     * <p>This matrix is type of CV_32SC1.</p>
     */
    SimpleSVM,

    /**
     * <p>Labels are stored in matrix which have 10 columns</p>
     * <p>
     * Values are coded like:<br>
     * - 1 - 100000000<br>
     * - 2 - 010000000<br>
     * - etc.<br>
     * </p>
     * <p>This matrix is type of CV_32FC1.</p>
     */
    Complex
}
