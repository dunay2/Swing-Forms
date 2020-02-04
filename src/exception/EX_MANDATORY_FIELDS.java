package exception;

@SuppressWarnings("serial")
public class EX_MANDATORY_FIELDS extends Exception { 
    public EX_MANDATORY_FIELDS(String errorMessage) {
        super(errorMessage);
    }
}