import java.util.LinkedList;
import java.util.Scanner;

public class ColaTrabajos {

    public static void main(String[] args) {

        LinkedList<String> trabajos = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("\n=== COLA DE TRABAJOS ===");
            System.out.println("1. Agregar trabajo normal");
            System.out.println("2. Agregar trabajo urgente");
            System.out.println("3. Procesar siguiente trabajo");
            System.out.println("4. Consultar siguiente trabajo");
            System.out.println("5. Mostrar trabajos pendientes");
            System.out.println("6. Mostrar número de trabajos");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Ingrese el trabajo: ");
                    String normal = scanner.nextLine();

                    trabajos.addLast(normal);

                    System.out.println("Trabajo agregado correctamente.");
                    break;

                case 2:
                    System.out.print("Ingrese el trabajo urgente: ");
                    String urgente = scanner.nextLine();

                    trabajos.addFirst(urgente);

                    System.out.println("Trabajo urgente agregado correctamente.");
                    break;

                case 3:
                    if (trabajos.isEmpty()) {
                        System.out.println("No hay trabajos pendientes.");
                    } else {
                        String procesado = trabajos.removeFirst();
                        System.out.println("Trabajo procesado: " + procesado);
                    }
                    break;

                case 4:
                    if (trabajos.isEmpty()) {
                        System.out.println("No hay trabajos pendientes.");
                    } else {
                        System.out.println("Siguiente trabajo: " + trabajos.getFirst());
                    }
                    break;

                case 5:
                    if (trabajos.isEmpty()) {
                        System.out.println("No hay trabajos pendientes.");
                    } else {
                        System.out.println("Trabajos pendientes:");
                        for (String trabajo : trabajos) {
                            System.out.println("- " + trabajo);
                        }
                    }
                    break;

                case 6:
                    System.out.println("Número de trabajos: " + trabajos.size());
                    break;

                case 7:
                    System.out.println("Programa terminado.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 7);

        scanner.close();
    }
}