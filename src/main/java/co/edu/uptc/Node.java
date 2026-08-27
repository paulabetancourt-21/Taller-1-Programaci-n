package co.edu.uptc;

import java.awt.image.BufferedImage;
import lombok.Data;

/**
 * Representa un nodo de una lista que contiene
 * una imagen y tiene una referencia al noto anterior y una
 * referencia al nodo siguiente
 *
 * @author Paula Betancourt
 * @author Claudia Garcia
 * @version 0.01
 */
@Data
public class Node {
    private BufferedImage image;
    private Node prev;
    private Node next;

    /**
     * inicializa el nodo con su respectiva imagen y deja 
     * la referencia anterior y siguiente vacia
     * @param image imagen que almacenara el nodo
     *
     */
    public Node(BufferedImage image) {
        this.image = image;
        this.prev = null;
        this.next = null;
    }
}