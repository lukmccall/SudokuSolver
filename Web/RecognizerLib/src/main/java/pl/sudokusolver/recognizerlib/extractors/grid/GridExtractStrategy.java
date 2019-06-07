package pl.sudokusolver.recognizerlib.extractors.grid;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;

/**
 * Abstrakcyjna reprezentacja algorytmów do extrakcji siatki sudoku
 */
public interface GridExtractStrategy {
    /**
     * @param img macierz, w której znajduje się zdjęcie. Macierz może mieć dowolny typ.
     * @return macierz zawierającą znalezioną siatkę. Macierz jest typu CV_8U1
     * @throws NotFoundSudokuException gdy nie udało się odnaleźć siatki
     */
    Mat extract(Mat img) throws NotFoundSudokuException;
}
