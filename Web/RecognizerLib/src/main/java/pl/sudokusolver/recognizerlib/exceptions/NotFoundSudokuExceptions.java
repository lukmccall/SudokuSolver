package pl.sudokusolver.recognizerlib.exceptions;

public class NotFoundSudokuExceptions extends Exception {
    public NotFoundSudokuExceptions(){
        super("Can't find sudoku");
    }

    public NotFoundSudokuExceptions(String message) {
        super(message);
    }

    public NotFoundSudokuExceptions(String message, Throwable throwable) {
        super(message, throwable);
    }

}
