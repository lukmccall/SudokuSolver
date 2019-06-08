package pl.sudokusolver.server.data;

public class NotOkResponse {
    public int status;
    public int errorCode;
    public String errorMessage;


    @Override
    public String toString() {
        return "" + status + " " + errorCode + " " + errorMessage;
    }
}
