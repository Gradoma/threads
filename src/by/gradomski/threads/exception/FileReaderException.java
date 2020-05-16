package by.gradomski.threads.exception;

public class FileReaderException extends Exception {
    static final long serialVersionUID = 1L;

    public FileReaderException(){
        super();
    }

    public FileReaderException(String message){
        super(message);
    }

    public FileReaderException(Exception e){
        super(e);
    }

    public FileReaderException(String message, Exception e){
        super(message, e);
    }
}
