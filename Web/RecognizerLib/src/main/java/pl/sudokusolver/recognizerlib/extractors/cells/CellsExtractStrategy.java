package pl.sudokusolver.recognizerlib.extractors.cells;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;

import java.util.List;

/**
 * Abstrakcyjna reprezentacja algorytmów do wycinania komórek z sudoku
 */
public interface CellsExtractStrategy {
    /**
     * @param grid macierz ze zdjęciem, na którym znajduje się jedynie siatka sudoku. Macierz powinna być kwadratowa oraz typu CV_8U lub CV_8UC1
     * @return lista wyciętych pól sudoku. Gdy udało się wyciąć poprawnie to lista posiada 81 elementów
     * @throws CellsExtractionFailedException gdy nie uda się wyciąć siatki
     */
    List<Mat> extract(Mat grid) throws CellsExtractionFailedException;
}
