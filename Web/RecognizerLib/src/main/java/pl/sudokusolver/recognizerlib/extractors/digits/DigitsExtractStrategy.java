package pl.sudokusolver.recognizerlib.extractors.digits;

import org.opencv.core.Mat;

import java.util.Optional;

/**
 * Abstrakcyjna reprezentacja algorytmów do extrakcji cyfr
 */
public interface DigitsExtractStrategy {
    /**
     * @param cell macierz z pojedynczą komórką sudoku. Macierz powinna być typu CV_8U lub CV_8UC1
     * @return macierz z cyfrą, gdy udało się ją znaleźć lub Optional.Empty() w przeciwnym wypadku
     */
    Optional<Mat> extractDigit(Mat cell);
}
