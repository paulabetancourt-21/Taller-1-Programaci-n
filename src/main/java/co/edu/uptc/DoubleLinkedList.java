package co.edu.uptc;

import java.awt.image.BufferedImage;

import org.slf4j.LoggerFactory;

import co.edu.uptc.Exceptions.InvalidImageException;
import lombok.Getter;
import org.slf4j.Logger;

/**
 * Representa una lista doblemente enlazada
 * contiene una referencia al inicio del nodo y el final
 *
 * @author Paula Betancourt
 * @author Claudia Garcia
 * @version 0.01
 */
@Getter
public class DoubleLinkedList {
    private static final Logger logger = LoggerFactory.getLogger(DoubleLinkedList.class);
    private Node head;
    private Node tail;

    /**
     * crea una lista doblemente enlazada vacia
     *
     */
    public DoubleLinkedList() {
        head = null;
        tail = null;
        logger.debug("Se ha inicializado una lista doblemente enlazada vacía");
    }

    /**
     * añade una imagen al final de la lista
     * @param image imagen que se va a almacenar en el nuevo nodo
     *
     */

    
    public void add(BufferedImage image) {
        if (image == null) {logger.error("Intento fallido de añadir la imagen nula a la lista");
            throw new InvalidImageException("La imagen no puede ser null");
        }
        Node newNode = new Node(image);
        if (head == null) {
            head = newNode;
            tail = newNode;
            logger.debug("Primer nodo (head y tail) añadido en la lista.");
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            logger.debug("Nuevo nodo añadido al final de la lista (Nodo tail actualizado)");
        }
        logger.info("Imagen agregada exitosamente");
    }
}