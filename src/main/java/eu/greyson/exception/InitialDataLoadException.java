package eu.greyson.exception;

/**
 * Thrown if application can not load data from file entered by input argument
 */
public class InitialDataLoadException extends Exception {

    private int errorCode;

    public InitialDataLoadException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
