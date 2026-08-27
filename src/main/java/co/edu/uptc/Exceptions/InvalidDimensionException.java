package co.edu.uptc.exceptions;

public class InvalidDimensionException extends RuntimeException{
    
    public InvalidDimensionException(String message) {
        super(message);
    }

    public InvalidDimensionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
