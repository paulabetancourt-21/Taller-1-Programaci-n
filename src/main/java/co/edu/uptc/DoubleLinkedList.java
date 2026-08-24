package co.edu.uptc;

import java.awt.image.BufferedImage;

import lombok.Getter;

@Getter
public class DoubleLinkedList {
    private Node head;
    private Node tail;

    public DoubleLinkedList() {
        head = null;
        tail = null;
    }

    public void add(BufferedImage image) {
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