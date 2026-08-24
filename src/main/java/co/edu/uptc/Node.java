package co.edu.uptc;

import java.awt.image.BufferedImage;

public class Node {
    private BufferedImage image;
    private Node prev;
    private Node next;

    public Node(BufferedImage image) {
        this.image = image;
        this.prev = null;
        this.next = null;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}