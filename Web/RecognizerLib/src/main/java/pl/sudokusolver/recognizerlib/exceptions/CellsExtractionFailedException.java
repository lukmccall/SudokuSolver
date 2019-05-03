package pl.sudokusolver.recognizerlib.exceptions;

import pl.sudokusolver.recognizerlib.extractors.cells.CellsExtractStrategy;

public class CellsExtractionFailedException extends Exception {

    public CellsExtractionFailedException(){
        super("Can't extract cells");
    }

    public CellsExtractionFailedException(String message) {
        super(message);
    }

    public CellsExtractionFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
