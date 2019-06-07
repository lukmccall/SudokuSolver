package pl.sudokusolver.recognizerlib.exceptions;

public class DigitExtractionFailedException extends Exception {

    public DigitExtractionFailedException(){
        super("Nie udało się wyciąć siatki sudoku.");
    }

    public DigitExtractionFailedException(String message) {
        super(message);
    }

    public DigitExtractionFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
