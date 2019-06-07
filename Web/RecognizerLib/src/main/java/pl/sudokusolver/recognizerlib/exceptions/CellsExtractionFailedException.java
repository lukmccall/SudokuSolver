package pl.sudokusolver.recognizerlib.exceptions;

public class CellsExtractionFailedException extends Exception {

    public CellsExtractionFailedException(){
            super("Nie udało się wyciąć komórek.");
    }

    public CellsExtractionFailedException(String message) {
        super(message);
    }

    public CellsExtractionFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
