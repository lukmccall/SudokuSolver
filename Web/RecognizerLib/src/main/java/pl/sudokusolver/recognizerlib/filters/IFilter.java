package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

/**
 * Interfejs filtrów
 */
public interface IFilter {
    /**
     * Zastosowanie filtra na macierzy
     * @param input macierz, na której stosujemy filter
     */
    void apply(Mat input);
}
