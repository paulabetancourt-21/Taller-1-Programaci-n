package co.edu.uptc;

import java.awt.image.BufferedImage;
import lombok.Data;

@Data
public class Node {
    private BufferedImage image;
    private Node prev;
    private Node next;

    public Node(BufferedImage image) {
        this.image = image;
        this.prev = null;
        this.next = null;
    }
}