package exception;


@SuppressWarnings("serial")
public class EX_MAX_LENGTH extends Exception { 
    public EX_MAX_LENGTH(String errorMessage) {
        super(errorMessage);
    }
}