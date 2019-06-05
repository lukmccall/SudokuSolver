package pl.sudokusolver.recognizerlib.sudoku;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;

public interface ISudokuExtractor {
    /**
     * @param url absolutna ścieżka do zdjęcia z sudoku
     * @return sudoku stworzone na podstawie zdjęcia
     * @throws NotFoundSudokuException gdy nie uda się znaleźć sudoku na zdjęciu
     * @throws CellsExtractionFailedException gdy nie uda się wyciąć komórek ze zdjęcia
     */
    Sudoku extract(String url) throws NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException;

    /**
     * @param img macierz z zdjęciem
     * @return sudoku stworzone na podstawie zdjęcia
     * @throws NotFoundSudokuException gdy nie uda się znaleźć sudoku na zdjęciu
     * @throws CellsExtractionFailedException gdy nie uda się wyciąć komórek ze zdjęcia
     */
    Sudoku extract(Mat img) throws NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException;

    Sudoku extract(Mat img,String path) throws NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException;

}
