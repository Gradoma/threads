package by.gradomski.threads.exception;

public class LogisticBaseException extends Exception {
    static final long serialVersionUID = 1L;

    public LogisticBaseException(){
        super();
    }

    public LogisticBaseException(String message){
        super(message);
    }

    public LogisticBaseException(Exception e){
        super(e);
    }

    public LogisticBaseException(String message, Exception e){
        super(message, e);
    }
}
