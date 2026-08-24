package co.edu.uptc;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ListManager {
    private DoubleLinkedList list;
    private int max;
    private int total;
    // Las variables sirven ?
    private int used;
    private int free;

    public ListManager() {
        list = new DoubleLinkedList();
        max = 0;
        total = 0;
        used = 0;
        free = 0;
    }

    public void add(BufferedImage image) {
        list.add(image);
        total++;
        if (list.getHead() != null) {
            used++;
            if (total > max) {
                max = total;
            }
        }
        free = max - used;
    }

    public void writeContent() {
        Node current = list.getHead();
        while (current != null) {
            BufferedImage img = current.getImage();
            String imgInfo = (img != null) ? img.getWidth() + "x" + img.getHeight() + " px" : "Sin imagen";
            System.out.println("Imagen: " + imgInfo);
            current = current.getNext();
        }
    }

    public void showImagesPopup() {
        Node current = list.getHead();
        if (current == null) {
            JOptionPane.showMessageDialog(null, "La lista está vacía", "Visualizador de Imágenes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int index = 1;
        while (current != null) {
            BufferedImage img = current.getImage();
            JLabel label = new JLabel(new ImageIcon(img));

            Object[] options = {"Anterior", "Siguiente", "Cerrar"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    label,
                    "Imagen Nodo " + index + " (" + img.getWidth() + "x" + img.getHeight() + " px)",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[1]
            );

            if (choice == 0) {
                if (current.getPrev() != null) {
                    current = current.getPrev();
                    index--;
                } else {
                    JOptionPane.showMessageDialog(null, "primer nodo de la lista.");
                }
            } else if (choice == 1) {
                if (current.getNext() != null) {
                    current = current.getNext();
                    index++;
                } else {
                    JOptionPane.showMessageDialog(null, "último nodo de la lista.");
                }
            } else {
                break;
            }
        }
    }

    // El metodo sirve ?
    public void memInfo() {
        System.out.println("maxima total used free");
        System.out.println(max + " " + total + " " + used + " " + free);
    }
}