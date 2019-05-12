package pl.sudokusolver.app.Api;

public class ErrorResponse {
    public int status;
    public int errorCode;
    public String errorMessage;


    @Override
    public String toString() {
        return "" + status + " " + errorCode + " " + errorMessage;
    }
}
