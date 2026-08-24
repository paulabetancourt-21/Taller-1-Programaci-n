# DynamicMemory

Aplicación en Java que demuestra el uso de una **lista doblemente enlazada** para gestionar una colección de objetos `BufferedImage` mientras analiza el uso de memoria.

## Características

- Implementa una `DoubleLinkedList` para almacenar objetos `BufferedImage`.
- Mide dinámicamente el uso de memoria mediante métricas del JVM.
- Visualiza imágenes en una ventana emergente con navegación (Anterior, Siguiente, Cerrar).
- Imprime un informe detallado de análisis de memoria, incluyendo consumo real frente al teórico.
- Usa `JOptionPane` para visualización interactiva de imágenes.

## Funcionamiento

1. La clase `Main` inicializa `ListDemo` y llama a `run()`.
2. `ListDemo` crea 5 imágenes de muestra con colores únicos y las añade a `ListManager`.
3. Se mide el uso de memoria antes y después de la inserción usando `Runtime.getRuntime().totalMemory()` y `freeMemory()`.
4. Se genera un informe de análisis de memoria con:
   - Memoria total utilizada
   - Memoria teórica (basada en cantidad de píxeles)
   - Sobrecarga debida a la gestión de memoria del JVM
5. `ListManager` muestra las imágenes en una ventana emergente con navegación.

## Uso

### Compilar y ejecutar
```bash
mvn clean package
java -jar target/DynamicMemory.jar 
```

### Clases clave

- `Main.java`: Punto de entrada.
- `ListDemo.java`: Controla la creación de imágenes, monitoreo de memoria y interfaz gráfica.
- `DoubleLinkedList.java`: Implementa una lista doblemente enlazada con referencias a nodos.
- `Node.java`: Almacena un `BufferedImage` y punteros al nodo anterior y siguiente.
- `ListManager.java`: Gestiona el estado de la lista y proporciona métodos para interactuar con las imágenes.

## Análisis de memoria

El programa calcula:
- **Delta de memoria real** desde el JVM.
- **Memoria teórica** basada en el número de píxeles: `ancho × alto × 4 bytes (ARGB)`.
- **Porcentaje de sobrecarga** para mostrar el costo de la gestión de memoria del JVM.

La medición de memoria usa `MemoryMXBean` para obtener una lectura más estable de la memoria heap y evitar resultados negativos causados por `System.gc()` antes de la medición inicial.

### Análisis de Memoria por Componente (Node e Imagen)

1. **Memoria que ocupa cada Nodo (Node)**

En una arquitectura JVM de 64 bits (con Compressed OOPs activado por defecto):

| Componente | Descripción | Tamaño |
|------------|-------------|--------|
| Cabecera del Objeto (Object Header) | Mark Word (8B) + Klass Word (4B) + Padding (4B) | 16 bytes |
| Referencia image | Puntero a la instancia de BufferedImage | 4 bytes |
| Referencia prev | Puntero al nodo anterior (Node) | 4 bytes |
| Referencia next | Puntero al nodo siguiente (Node) | 4 bytes |
| Relleno (Padding) | Alineación del objeto a múltiplos de 8 bytes | 4 bytes |
| **TOTAL POR NODO** | Estructura base del nodo | **32 bytes** |

2. **Memoria que ocupa cada Imagen (BufferedImage 100x100 TYPE_INT_ARGB)**

Una BufferedImage es una estructura de objetos interconectados en memoria:

| Componente | Cálculo | Tamaño aproximado |
|------------|---------|-------------------|
| Matriz de Píxeles (int[]) | 100 × 100 píxeles × 4 bytes/píxel + header (16B) | 40,016 bytes |
| Instancia BufferedImage | Atributos de dimensiones y banderas de estado | ∼ 64 bytes |
| Objeto WritableRaster | Control y mapeo de coordenadas | ∼ 48 bytes |
| Objeto DataBufferInt | Envoltorio del arreglo primario | ∼ 32 bytes |
| Metadatos de Color (ColorModel / SampleModel) | Definición de canales R, G, B y Alfa | ∼ 300 bytes |
| **TOTAL POR IMAGEN** | Datos de píxeles + Objetos auxiliares | ∼ 40,460 bytes (≈ 40.5 KB) |

3. **Total por Elemento de la Lista**

Estructura Nodo (32 B) + Objeto Imagen (40,460 B) ≈ 40,492 bytes (≈ 40.52 KB / elemento)

Para 5 Elementos:
- Memoria neta real retenida: 5 × 40.52 KB ≈ 202.6 KB.

4. **Origen de los valores altos observados en el delta inicial**

El valor elevado observado en la prueba con 5 elementos se divide en:
- Datos de los Nodos e Imágenes: ∼ 0.2 MB (202.6 KB).
- Carga Inicial del Motor Gráfico Java2D / AWT: ∼ 2.8 MB.
  - La primera vez que se instancian componentes de `java.awt.image`, la JVM realiza una inicialización perezosa (lazy loading) de librerías nativas, tablas de color y buffers del sistema gráfico. Este costo ocurre una única vez.

## Nota sobre la medición

La salida de memoria puede variar entre ejecuciones porque la JVM administra la heap de forma dinámica.  
