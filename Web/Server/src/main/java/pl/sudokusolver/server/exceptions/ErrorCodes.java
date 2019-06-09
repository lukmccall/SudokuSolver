package pl.sudokusolver.server.exceptions;

/**
 * Errors code for server
 */
public enum ErrorCodes {
    Unknown(0),
    PageNotFound(13),
    MissingParameter(14),
    InvalidParameter(15),
    FileIsCorrupted(11),
    SudokuNotFound(3),
    CellsExtractionFailed(16),
    SolverFailed(4);

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
