package exception;

public class EX_INVALID_DATE extends Exception { 
    public EX_INVALID_DATE(String errorMessage) {
        super(errorMessage);
    }
}