package co.edu.uptc.exceptions;

/**
 * Excepción lanzada cuando se intenta realizar una operación sobre una lista
 * de imágenes que no contiene elementos.
 */
public class EmptyListException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message mensaje que describe la causa del error
     */
    public EmptyListException(String message) {
        super(message);
    }

    /**
     * Construye la excepción con un mensaje descriptivo y la causa original
     * que la originó.
     *
     * @param mensaje mensaje que describe la causa del error
     * @param causa excepción original que provocó este error
     */
    public EmptyListException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

}