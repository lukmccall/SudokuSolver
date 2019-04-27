package pl.sudokusolver.server.exceptions;

public enum ErrorCodes {
    Unknown(0),NotFound(1), MissingParameter(2);

    private final int code;
    private ErrorCodes(int value) {
        this.code = value;
    }

    public int getValue() {
        return code;
    }
}
