package co.edu.uptc.exceptions;

public class InvalidImageException extends RuntimeException{
    
    public InvalidImageException(String message) {
        super(message);
    }

    public InvalidImageException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
