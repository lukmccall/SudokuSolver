package pl.sudokusolver.recognizerlib.exceptions;

public class VersionMismatchException extends Exception {
    public VersionMismatchException(String message) {
        super(message);
    }

    public VersionMismatchException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
