package co.edu.uptc;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * Asistente que adminiostra una lista doblemente enlazada de imagenes y muestra
 * la interfaz grafica para manejar cada una de ellas
 * 
 * @author Paula Betancourt
 * @author Claudia Garcia
 * @version 0.01
 */

public class ListManager {
    private DoubleLinkedList list;
    private int max;
    private int total;
    // Las variables sirven ?
    private int used;
    private int free;

    // NOTE: No tiene sentido el max, used, y free, free siempre sera cero. Used siempre sera igual que total, no tiene sentido

      /**
     * inicializa la lista doblemente enlazada 
     *
     */
    public ListManager() {
        list = new DoubleLinkedList();
        max = 0;
        total = 0;
        used = 0;
        free = 0;
    }

      /**
     * Añade la imagen al final de la lista
     * @param image imagen que se añade a la lista 
     *
     */
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

      /**
     * recorre la lista de imagenes para mostrar la informacion de dimensiones de cada una
     *
     */
    public void writeContent() {
        Node current = list.getHead();
        while (current != null) {
            BufferedImage img = current.getImage();
            String imgInfo = (img != null) ? img.getWidth() + "x" + img.getHeight() + " px" : "Sin imagen";
            System.out.println("Imagen: " + imgInfo);
            current = current.getNext();
        }
    }

  /**
     * Manejo de la interfaz grafica para mostrar las imagenes mediante ventanas emergentes 
     * permitiendo navegar entre los nodos
     *
     */
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

    // NOTE: Es codigo basura, no esta haciendo nada en el codigo
    public void memInfo() {
        System.out.println("maxima total used free");
        System.out.println(max + " " + total + " " + used + " " + free);
    }
}