package pl.sudokusolver.recognizerlib.exceptions;

public class DigitExtractionFailedException extends Exception {

    public DigitExtractionFailedException(){
        super("Can't extract digit");
    }

    public DigitExtractionFailedException(String message) {
        super(message);
    }

    public DigitExtractionFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
