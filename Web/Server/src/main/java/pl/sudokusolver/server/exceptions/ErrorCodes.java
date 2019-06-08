package pl.sudokusolver.server.exceptions;

/**
 * Errors code for server
 */
public enum ErrorCodes {
    Unknown(0), PageNotFound(1), MissingParameter(2), InvalidParameter(3), FileIsCorrupted(4),
    SudokuNotFound(5), CellsExtractionFailed(6), SolverFailed(7);

    private final int code;
    ErrorCodes(int value) {
        this.code = value;
    }

    /**
     * @return error code
     */
    public int getValue() {
        return code;
    }
}
