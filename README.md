
OneDrive
Vista previa de README.md
Práctica: ArrayList vs. LinkedList en Java Collections Framework
1. Propósito
ArrayList y LinkedList implementan la interfaz List, pero utilizan estructuras internas diferentes. La práctica busca que compruebes experimentalmente cómo estas diferencias afectan el acceso, inserción, eliminación y recorrido.
Al finalizar, serás capaz de utilizar List, ArrayList y LinkedList; explicar sus diferencias estructurales; relacionar operaciones con complejidad temporal; medir empíricamente su desempeño; y justificar la elección de una implementación.
2. Situación problema
Una aplicación mantiene una lista de tareas pendientes y realiza operaciones de inserción al inicio y al final, acceso por posición, eliminación y recorrido. Se investigará cuándo resulta más apropiado utilizar ArrayList o LinkedList.
3. Preparación en IntelliJ IDEA
Crear el proyecto y su respectibo repositorio:
ComparacionListas/
└── src/
    ├── EjemploArrayList.java
    ├── EjemploLinkedList.java
    ├── ComparacionListas.java
    ├── BenchmarkListas.java
    ├── EjemploDeque.java
    └── ColaTrabajos.java
4. Experimento con ArrayList
import java.util.ArrayList;
import java.util.List;

public class EjemploArrayList {
    public static void main(String[] args) {
        List<String> tareas = new ArrayList<>();

        tareas.add("Preparar presentación");
        tareas.add("Revisar código");
        tareas.add("Actualizar documentación");
        tareas.add("Ejecutar pruebas");
        tareas.add("Publicar versión");

        System.out.println(tareas);
        System.out.println("Primera: " + tareas.get(0));
        System.out.println("Tercera: " + tareas.get(2));

        tareas.set(1, "Revisar código Java");
        tareas.add(0, "Revisar correo");
        tareas.add(3, "Reunión de seguimiento");

        System.out.println(tareas);
    }
}
ArrayList utiliza conceptualmente un arreglo dinámico:
Índice       0       1       2       3       4
             ▼       ▼       ▼       ▼       ▼
          ┌───────┬───────┬───────┬───────┬───────┐
ArrayList │   A   │   B   │   C   │   D   │   E   │
          └───────┴───────┴───────┴───────┴───────┘
Pregunta: ¿Qué ocurre internamente al insertar en la posición 0? ¿Por qué el acceso por índice puede realizarse eficientemente? R.
5. Experimento con LinkedList
import java.util.LinkedList;
import java.util.List;

public class EjemploLinkedList {
    public static void main(String[] args) {
        List<String> tareas = new LinkedList<>();

        tareas.add("Preparar presentación");
        tareas.add("Revisar código");
        tareas.add("Actualizar documentación");
        tareas.add("Ejecutar pruebas");
        tareas.add("Publicar versión");

        System.out.println(tareas);
    }
}
LinkedList es una lista doblemente enlazada:
┌─────┐     ┌─────┐     ┌─────┐     ┌─────┐
│  A  │ ⇄   │  B  │ ⇄   │  C  │ ⇄   │  D  │
└─────┘     └─────┘     └─────┘     └─────┘
6. Comparación funcional
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ComparacionListas {
    public static void main(String[] args) {
        probarLista(new ArrayList<>());
        System.out.println("--------------------");
        probarLista(new LinkedList<>());
    }

    public static void probarLista(List<String> lista) {
        lista.add("A");
        lista.add("B");
        lista.add("C");
        lista.add(0, "INICIO");
        lista.add("FINAL");

        System.out.println(lista);
        System.out.println("Elemento 2: " + lista.get(2));

        lista.remove(1);
        System.out.println(lista);
    }
}
Analice por qué probarLista(List<String> lista) puede trabajar con ambas implementaciones.
7. Complejidad temporal
Operación	ArrayList	LinkedList
get(i)	O(1)	O(n)
set(i,x)	O(1)	O(n)
add(x) al final	O(1) amortizado	O(1)
add(0,x)	O(n)	O(1)
remove(0)	O(n)	O(1)
búsqueda por valor	O(n)	O(n)
recorrido completo	O(n)	O(n)
La complejidad asintótica no implica que una implementación sea siempre más rápida. En LinkedList, una inserción en una posición intermedia requiere primero localizar el nodo, lo que puede costar O(n).
8. Benchmark de acceso
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BenchmarkListas {
    private static final int N = 100_000;

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        llenar(arrayList);
        llenar(linkedList);

        medirAcceso("ArrayList", arrayList);
        medirAcceso("LinkedList", linkedList);
    }

    private static void llenar(List<Integer> lista) {
        for (int i = 0; i < N; i++) {
            lista.add(i);
        }
    }

    private static void medirAcceso(String nombre, List<Integer> lista) {
        long inicio = System.nanoTime();
        long suma = 0;

        for (int i = 0; i < lista.size(); i++) {
            suma += lista.get(i);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
        System.out.println("Suma: " + suma);
    }
}
Ejecute al menos tres veces y registre:
Ejecución	ArrayList	LinkedList
1	8.821 ms	4600.414 ms
2	9.003 ms	4200.287 ms
3	9.673 ms	4199.134 ms
Promedio	9.166 ms	4333.278 ms
Después sustituya el recorrido mediante get(i) por:
for (Integer valor : lista) {
    suma += valor;
}
Compare nuevamente y registre resultados
Ejecución	ArrayList	LinkedList
1	10.159	5.702
2	9.023	3.731
3	8.274	3.965
Promedio	9.152	4.466
9. Inserciones al inicio
private static void medirInsercionInicio(
        String nombre, List<Integer> lista) {

    long inicio = System.nanoTime();

    for (int i = 0; i < 50_000; i++) {
        lista.add(0, i);
    }

    long fin = System.nanoTime();

    System.out.printf("%s: %.3f ms%n",
            nombre, (fin - inicio) / 1_000_000.0);
}
Ejecute con:
medirInsercionInicio("ArrayList", new ArrayList<>());
medirInsercionInicio("LinkedList", new LinkedList<>());
Formule una hipótesis antes de ejecutar y compare con los resultados.
10. Inserciones al final
private static void medirInsercionFinal(
        String nombre, List<Integer> lista) {

    long inicio = System.nanoTime();

    for (int i = 0; i < 100_000; i++) {
        lista.add(i);
    }

    long fin = System.nanoTime();

    System.out.printf("%s: %.3f ms%n",
            nombre, (fin - inicio) / 1_000_000.0);
}
Registre los resultados:
Operación	ArrayList	LinkedList
Insertar al inicio	166.342 ms	3.900 ms
Insertar al final	4.945 ms	14.275 ms
11. Eliminaciones
Prepare dos listas con el mismo número de elementos y mida:
while (!lista.isEmpty()) {
    lista.remove(0);
}
Analice por qué ArrayList debe desplazar elementos y LinkedList puede modificar los enlaces del primer nodo.
12. LinkedList como Deque
LinkedList también implementa Deque.
import java.util.LinkedList;

public class EjemploDeque {
    public static void main(String[] args) {
        LinkedList<String> tareas = new LinkedList<>();

        tareas.addFirst("Primera");
        tareas.addLast("Última");
        tareas.addFirst("Urgente");

        System.out.println(tareas);
        System.out.println("Primera: " + tareas.getFirst());
        System.out.println("Última: " + tareas.getLast());

        tareas.removeFirst();
        System.out.println(tareas);
    }
}
13. Actividad integradora: Sistema de cola de trabajos
Desarrolle ColaTrabajos.java para administrar trabajos enviados a un servidor.
El sistema deberá permitir:
1. Agregar trabajo normal
2. Agregar trabajo urgente
3. Procesar siguiente trabajo
4. Consultar siguiente trabajo
5. Mostrar trabajos pendientes
6. Mostrar número de trabajos
7. Salir
Primera implementación
List<String> trabajos = new ArrayList<>();
Operaciones:
trabajos.add(trabajo);       // normal
trabajos.add(0, trabajo);    // urgente
trabajos.get(0);             // consultar siguiente
trabajos.remove(0);          // procesar
Segunda implementación
Cambie únicamente la implementación:
List<String> trabajos = new LinkedList<>();
Compruebe que el comportamiento funcional permanece y analice las diferencias de desempeño.
14. Mejora del diseño
Analice si realmente se necesita List. El problema requiere principalmente insertar al inicio y al final, consultar el inicio y eliminar el inicio.
Refactorice utilizando:
Deque<String> trabajos = new LinkedList<>();
y las operaciones:
addFirst()
addLast()
peekFirst()
pollFirst()
Ejemplo:
trabajos.addLast("Ejecutar pruebas");
trabajos.addFirst("Corregir servidor");

String siguiente = trabajos.peekFirst();
String procesado = trabajos.pollFirst();
La elección no consiste únicamente en decidir entre ArrayList y LinkedList; primero debe seleccionarse la abstracción adecuada para las operaciones requeridas.
15. Tabla comparativa final
Característica	ArrayList	LinkedList
Implementa List	Sí	Sí
Estructura	Arreglo dinámico	Lista doblemente enlazada
Acceso get(i)	O(1)	O(n)
Modificación set(i)	O(1)	O(n)
Inserción al final	O(1) amortizado	O(1)
Inserción al inicio	O(n)	O(1)
Eliminación al inicio	O(n)	O(1)
Búsqueda por valor	O(n)	O(n)
Recorrido completo	O(n)	O(n)
Implementa Deque	No	Sí
Memoria adicional por elemento	Menor en general	Mayor por los enlaces
Acceso aleatorio frecuente	Adecuado	Poco adecuado
Operaciones frecuentes en extremos	No es su principal fortaleza	Adecuado
16. Preguntas de análisis
1.	¿Qué interfaz implementan tanto ArrayList como LinkedList? R. Ambas implementan la interfaz List
2.	¿Cuál es la principal diferencia en su estructura interna? R. ArrayList utiliza un arreglo dinámico, mientras que LinkedList utiliza una lista doblemente enlazada
3.	¿Por qué ArrayList.get(i) tiene complejidad O(1)? R. Porque los elementos están almacenados en posiciones contiguas y se puede acceder directamente a una posición mediante su índice.
4.	¿Por qué LinkedList.get(i) tiene complejidad O(n)?R. Porque para llegar a una posición determinada debe recorrer los nodos de la lista hasta encontrar el índice solicitado.
5.	¿Qué ocurre internamente cuando se ejecuta ArrayList.add(0, elemento)? R.
Los elementos existentes deben desplazarse una posición para dejar espacio al nuevo elemento en el índice 0.
6.	¿Por qué LinkedList.add(0, elemento) no necesita desplazar los demás elementos?R. Porque solamente se modifican los enlaces entre el nuevo primer nodo y el nodo que anteriormente estaba al inicio.
7.	¿Por qué afirmar que "LinkedList es mejor para inserciones" puede ser incorrecto? R. Porque depende de dónde se inserte y de cómo se llegue a esa posición. LinkedList es eficiente en los extremos, pero acceder a posiciones intermedias puede requerir recorrer la lista
8.	¿Qué diferencia observó entre recorrer LinkedList mediante get(i) y mediante for-each?R. Con get(i), LinkedList presentó tiempos mucho mayores. Con for-each, el recorrido fue mucho más rápido porque se avanza directamente mediante los enlaces de la lista.
En nuestros resultados, LinkedList pasó aproximadamente de 4333.278 ms con get(i) a 4.466 ms con for-each.
9.	¿Qué resultados obtuvo para inserciones al inicio?R. ArrayList: 166.342 ms. LinkedList: 3.900 ms. En esta prueba LinkedList tuvo un tiempo menor.¿Qué resultados obtuvo para inserciones al final?R. ArrayList: 4.945 ms. LinkedList: 14.275 ms. En esta ejecución ArrayList tuvo un tiempo menor.
10.	¿Los tiempos medidos coinciden exactamente con lo esperado a partir de Big-O?R. No necesariamente coinciden exactamente. Big-O describe el crecimiento de la operación, pero los tiempos reales dependen también del hardware, la JVM, la memoria y otras condiciones de ejecución. Los resultados obtenidos muestran las tendencias esperadas, aunque los valores pueden variar entre ejecuciones.
11.	¿Qué costo de memoria adicional tiene conceptualmente una lista enlazada?RCada nodo necesita almacenar referencias a otros nodos, por lo que LinkedList requiere memoria adicional para sus enlaces.
12.	¿Qué ventajas proporciona programar contra List?R. Permite trabajar con diferentes implementaciones, como ArrayList y LinkedList, utilizando la misma interfaz. Esto facilita cambiar la implementación sin modificar necesariamente el código que utiliza la lista.
13.	¿Por qué Deque representa mejor el problema de la cola de trabajos?R. Porque el problema necesita operaciones en ambos extremos y específicamente agregar al inicio y al final, consultar el primero y eliminar el primero. Deque proporciona operaciones como addFirst(), addLast(), peekFirst() y pollFirst()
14.	¿En qué escenario seleccionaría ArrayList?R. Cuando se necesita acceder frecuentemente a los elementos mediante índices y realizar recorridos o accesos aleatorios.
15.	¿En qué escenario tendría sentido utilizar LinkedList?R. Cuando las operaciones frecuentes se realizan en los extremos de la estructura, especialmente cuando se necesitan inserciones o eliminaciones al inicio
17. Entregables
ComparacionListas/
├── README.md
└── src/
    ├── EjemploArrayList.java
    ├── EjemploLinkedList.java
    ├── ComparacionListas.java
    ├── BenchmarkListas.java
    ├── EjemploDeque.java
    └── ColaTrabajos.java
En README.md incluir:
•	tabla con los tiempos obtenidos;
•	comparación entre acceso mediante get(i) y for-each;
•	resultados de inserciones y eliminaciones;
•	tabla comparativa final;
•	respuestas a las preguntas de análisis;
•	conclusión técnica.

conclusión tcnica:
durante la práctica se comprobó experimentalmente que arraylist y linkedlist implementan la misma interfaz list, pero tienen estructuras internas diferentes y, por lo tanto, distintos costos de operación.
los resultados mostraron una diferencia importante en el acceso mediante get(i): arraylist obtuvo un promedio de aproximadamente 9.166 ms, mientras que linkedlist obtuvo aproximadamente 4333.278 ms. en cambio, al recorrer las listas mediante for-each, los tiempos fueron mucho más cercanos: aproximadamente 9.152 ms para arraylist y 4.466 ms para linkedlist
en las operaciones de modificación también se observaron diferencias. para la inserción al inicio, linkedlist obtuvo un menor tiempo en nuestra medición (3.900 ms) frente a arraylist (166.342 ms). para la inserción al final, en esta ejecución arraylist obtuvo 4.945 ms y linkedlist 14.275 ms. en la eliminación al inicio, arraylist obtuvo 806.885 ms, mientras que linkedlist obtuvo 6.128 ms
estos resultados permiten relacionar el comportamiento observado con la complejidad temporal de cada estructura. arraylist es apropiada cuando se necesita acceso frecuente mediante índices, mientras que linkedlist resulta adecuada para operaciones frecuentes en los extremos. por ello, la elección de la estructura debe depender de las operaciones que realizará principalmente el programa y no solamente de la implementación
finalmente, la práctica permitió comprobar que programar utilizando la interfaz list facilita cambiar entre diferentes implementaciones y que, para el problema de una cola de trabajos con operaciones en ambos extremos, una abstracción como deque representa mejor las operaciones requeridas.
