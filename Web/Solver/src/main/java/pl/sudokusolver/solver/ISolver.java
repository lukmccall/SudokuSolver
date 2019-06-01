package pl.sudokusolver.solver;

/**
 * Podstawowy interfejs obudowujący wszystkie solvery.
 */
public interface ISolver {
    /**
     * @param sudoku dwu wymiarowa tablica z danymi wejściowymi, w niej również zapisywany jest wynik
     * @return true jeśli uda się roziązać sudoku, w przeciwnym wypadku false
     */
    boolean solve(int[][] sudoku);
}
