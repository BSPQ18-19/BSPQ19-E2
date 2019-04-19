package es.deusto.spq.server.exceptions;

public class SignupException extends Exception {

    public static final int UNKNOWN = 0;
    public static final int VALIDATION = 1;

    private int reason;

    public SignupException() {
    }

    public SignupException(int reason) {
        this.reason = reason;
    }

    public int getReason() {
        return reason;
    }
}
