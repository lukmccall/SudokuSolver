package pl.sudokusolver.recognizerlib.data;

/**
 * Typy danych używanych do nauczania ocrów.<br>
 * Typ odnosi się do tego jak przedstawione są etykiety<br>
 * Dostępne typy:
 * <ul>
 *     <li>{@link #Simple}</li>
 *     <li>{@link #SimpleSVM}</li>
 *     <li>{@link #Complex}</li>
 * </ul>
 */
public enum DataType {
    /**
     * <p>Etykity są przechowywane w macierzy, która posiada jedną columne.</p>
     * <p>Macierz jest typu CV_32FC1.</p>
     */
    Simple,

    /**
     * <p>Etykity są przechowywane w macierzy, która posiada jedną columne.</p>
     * <p>Macierz jest typu CV_32SC1.</p>
     */
    SimpleSVM,

    /**
     * <p>Etykiety są przechowywane w macierzy, która ma 10 kolumn, w której znajduje się zakodowana etykieta.</p>
     * <p>
     * Sposób kodowania:<br>
     * - 1 - 1000000000<br>
     * - 2 - 0100000000<br>
     * </p>
     * <p>Macierz jest typu CV_32FC1.</p>
     */
    Complex
}
