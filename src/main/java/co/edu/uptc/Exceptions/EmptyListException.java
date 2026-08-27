package co.edu.uptc.exceptions;

public class EmptyListException  extends RuntimeException{
    
    public EmptyListException(String message) {
        super(message);
    }

    public EmptyListException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
