package pl.sudokusolver.server.exceptions;

public class ErrorRsponse {
    private int errorCode;
    private String errorMessage;

    public ErrorRsponse(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorRsponse(ErrorCodes errorCode, String errorMessage){
        this.errorCode = errorCode.getValue();
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
