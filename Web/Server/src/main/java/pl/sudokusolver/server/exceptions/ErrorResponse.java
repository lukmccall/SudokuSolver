package pl.sudokusolver.server.exceptions;

/**
 * Class used for generated json response if something goes wrong :\
 */
public class ErrorResponse {
    /**
     * error code.
     */
    private int errorCode;

    /**
     * error message.
     */
    private String errorMessage;

    /**
     * Create object form given parameters.
     * @param errorCode code.
     * @param errorMessage message.
     */
    public ErrorResponse(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Create object form given parameters.
     * @param errorCode code.
     * @param errorMessage message.
     */
    public ErrorResponse(ErrorCodes errorCode, String errorMessage){
        this.errorCode = errorCode.getValue();
        this.errorMessage = errorMessage;
    }

    /**
     * @return error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage new message.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return error code.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode new error code.
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
