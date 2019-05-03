package pl.sudokusolver.server.exceptions;

public enum ErrorCodes {
    Unknown(0), PageNotFound(1), MissingParameter(2), InvalidParameter(3), FileIsCorrupted(4),
    SudokuNotFound(5), CellsExtractionFailed(6), SolverFailed(7);

    private final int code;
    private ErrorCodes(int value) {
        this.code = value;
    }

    public int getValue() {
        return code;
    }
}
