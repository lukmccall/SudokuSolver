package pl.sudokusolver.server.exceptions;

public class SolvingFailedException extends Exception {
    public SolvingFailedException() {
        super("Nie udało się rozwiązać sudoku.");
    }

    public SolvingFailedException(String message) {
        super(message);
    }

    public SolvingFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}