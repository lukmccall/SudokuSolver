package pl.sudokusolver.recognizerlib.exceptions;

/**
 * Couldn't find soudoku.
 */
public class NotFoundSudokuException extends Exception {
    public NotFoundSudokuException(){
        super("Nie udało się rozpoznać sudoku.");
    }

    public NotFoundSudokuException(String message) {
        super(message);
    }

    public NotFoundSudokuException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
