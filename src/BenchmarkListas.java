import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BenchmarkListas {

    private static final int N = 100_000;

    public static void main(String[] args) {

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        List<Integer> arrayParaEliminar = new ArrayList<>();
        List<Integer> linkedParaEliminar = new LinkedList<>();

        llenar(arrayParaEliminar);
        llenar(linkedParaEliminar);

        medirEliminacionInicio("ArrayList", arrayParaEliminar);
        medirEliminacionInicio("LinkedList", linkedParaEliminar);

        llenar(arrayList);
        llenar(linkedList);

        // Acceso mediante get(i)
        medirAcceso("ArrayList", arrayList);
        medirAcceso("LinkedList", linkedList);

        // Inserción al inicio
        medirInsercionInicio("ArrayList", new ArrayList<>());
        medirInsercionInicio("LinkedList", new LinkedList<>());

        // Inserción al final
        medirInsercionFinal("ArrayList", new ArrayList<>());
        medirInsercionFinal("LinkedList", new LinkedList<>());
    }

    private static void llenar(List<Integer> lista) {

        for (int i = 0; i < N; i++) {
            lista.add(i);
        }
    }

    private static void medirAcceso(
            String nombre, List<Integer> lista) {

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
    private static void medirEliminacionInicio(
            String nombre, List<Integer> lista) {

        long inicio = System.nanoTime();

        while (!lista.isEmpty()) {
            lista.remove(0);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
    }
}