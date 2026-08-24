package co.edu.uptc;

import java.awt.image.BufferedImage;

import co.edu.uptc.Exceptions.InvalidImageException;
import lombok.Getter;

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
    private Node head;
    private Node tail;

    /**
     * crea una lista doblemente enlazada vacia
     *
     */
    public DoubleLinkedList() {
        head = null;
        tail = null;
    }

    /**
     * añade una imagen al final de la lista
     * @param image imagen que se va a almacenar en el nuevo nodo
     *
     */

    
    public void add(BufferedImage image) {
        if (image == null) {
            throw new InvalidImageException("La imagen no puede ser null");
        }
        Node newNode = new Node(image);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
    }
}