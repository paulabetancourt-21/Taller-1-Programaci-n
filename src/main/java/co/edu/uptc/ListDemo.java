package co.edu.uptc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * Genera una lista doblemente enlazada con imagenes aleatorias y muestra
 * un analisis de la memoria
 * 
 * @author Paula Betancourt
 * @author Claudia Garcia
 * @version 0.01
 */
public class ListDemo {
    private long initialUsedBytes = 0;
    private long finalUsedBytes = 0;
    private final int TOTAL_ELEMENTS = 5;
    private final int IMAGE_WIDTH = 100;
    private final int IMAGE_HEIGHT = 100;

    /**
     * Inicia el programa añadiendo la imagen para ir generando la lista
     *
     */
    public void run() {
        memInfo("Inicial");
        ListManager manager = new ListManager();

        for (int i = 0; i < TOTAL_ELEMENTS; i++) {
            BufferedImage img = createSampleImage(IMAGE_WIDTH, IMAGE_HEIGHT, i);
            manager.add(img);
        }

        memInfo("Final");
        printAnalysis();
        manager.showImagesPopup();
    }

    /**
     * Utiliza java 2d para crear la imagen que se añadira al nodo
     * @param width ancho de la imagen
     * @param height alto de la imagen 
     * @param index numero para generar el color aleatorio
     * @return imagen generada
     *
     */
    private BufferedImage createSampleImage(int width, int height, int index) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setColor(new Color((index * 37) % 256, (index * 59) % 256, (index * 83) % 256));
        g2d.fillRect(0, 0, width, height);

        String text = String.valueOf(index);
        g2d.setColor(Color.WHITE);
        java.awt.FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, x, y);

        g2d.dispose();
        return img;
    }

    /**
     * Información respecto al uso de memoria en la ejecución del programa
     * @param etapa informa si esta en el inicio de la ejecucion o en el final
     *
     */
    public void memInfo(String etapa) {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        long usedBytes = heapUsage.getUsed();

        if (initialUsedBytes == 0) {
            initialUsedBytes = usedBytes;
            finalUsedBytes = usedBytes;
        } else {
            finalUsedBytes = usedBytes;
        }

        long deltaBytes = finalUsedBytes - initialUsedBytes;

        String maxStr = (heapUsage.getMax() == -1)
                ? "Sin límite"
                : String.format("%,d MB", heapUsage.getMax() / (1024 * 1024));
        String deltaStr = (initialUsedBytes == finalUsedBytes)
                ? "—"
                : String.format("%+,d bytes (%+.2f MB)", deltaBytes, (double) deltaBytes / (1024 * 1024));

        String fmt = "%-10s %-20s %-20s %-20s %-20s %-35s%n";
        if (initialUsedBytes == finalUsedBytes) {
            System.out.printf(fmt, "Etapa", "Máxima (Max)", "Total (Total)", "Usada (Used)", "Libre (Free)",
                    "Delta vs Inicial");
            System.out.println("-".repeat(125));
        }

        System.out.printf(fmt,
                etapa,
                maxStr,
                String.format("%,d MB", heapUsage.getCommitted() / (1024 * 1024)),
                String.format("%,d bytes (%.2f MB)", usedBytes, (double) usedBytes / (1024 * 1024)),
                String.format("%,d MB", (heapUsage.getCommitted() - usedBytes) / (1024 * 1024)),
                deltaStr);
    }

    /**
     * Muestra en consola el analisis de la memoria despues de generar
     * la lista con las imagenes
     *
     */
    public void printAnalysis() {
        long deltaBytes = finalUsedBytes - initialUsedBytes;
        double bytesPerElement = (double) deltaBytes / TOTAL_ELEMENTS;

        long theoreticalBytesPerImage = (long) IMAGE_WIDTH * IMAGE_HEIGHT * 4;
        long theoreticalTotal = theoreticalBytesPerImage * TOTAL_ELEMENTS;
        double overhead = (deltaBytes > 0)
                ? ((double) (deltaBytes - theoreticalTotal) / theoreticalTotal) * 100
                : 0;

        System.out.println("\n--- ANÁLISIS DE MEMORIA ---");
        System.out.printf("Elementos insertados             : %,d%n", TOTAL_ELEMENTS);
        System.out.printf("Delta real (JVM)                 : %,d bytes (%.2f MB)%n", deltaBytes,
                (double) deltaBytes / (1024 * 1024));
        System.out.printf("Tamaño teórico total (solo pixeles): %,d bytes (%.2f MB)%n", theoreticalTotal,
                (double) theoreticalTotal / (1024 * 1024));
        System.out.printf("Overhead JVM estimado            : %.1f%%%n", overhead);
        System.out.printf("Promedio real por elemento       : %.2f bytes/elemento%n", bytesPerElement);
        System.out.printf("Tamaño teórico por imagen        : %,d bytes/elemento (%dx%d px ARGB)%n",
                theoreticalBytesPerImage, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
}
