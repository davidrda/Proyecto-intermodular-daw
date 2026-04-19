package view;

import model.Clase;
import service.ClaseService;

import java.util.List;
import java.util.Scanner;

public class MenuClases {

    private ClaseService claseService;
    private Scanner scanner;

    public MenuClases() {
        claseService = new ClaseService();
        scanner = new Scanner(System.in);
    }

    public void mostrar() {

        int opcion = 0;

        do {
            System.out.println("""
                === MENÚ CLASES ===
                | 0. Salir
                | 1. Insertar
                | 2. Listar todas
                | 3. Buscar por ID
                | 4. Actualizar
                | 5. Eliminar
                
                Elige una opción:
                """);

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> insertar();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> actualizar();
                case 5 -> eliminar();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    private void insertar() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el ID del entrenador: ");
        int idEntrenador = scanner.nextInt();

        System.out.println("Inserta el ID de la sala: ");
        int idSala = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Inserta el nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("Inserta el nivel: ");
        String nivel = scanner.nextLine();

        System.out.println("Inserta la duración (en minutos): ");
        int duracion = scanner.nextInt();

        Clase clase = new Clase(idEntrenador, idSala, nombre, nivel, duracion);

        try {
            claseService.insertar(clase);
            System.out.println("Clase insertada correctamente");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarTodos() {
        List<Clase> clases = claseService.listarTodos();
        if (clases.isEmpty()) {
            System.out.println("No hay clases en la lista");
        } else {
            for (Clase c : clases) {
                System.out.println(c);
            }
        }
    }

    private void buscarPorId() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            Clase clase = claseService.buscarPorId(id);
            System.out.println(clase);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void actualizar() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el ID de la clase a actualizar: ");
        int id = scanner.nextInt();

        try {
            Clase clase = claseService.buscarPorId(id);

            System.out.println("Clase actual: " + clase);
            System.out.println("Introduce los nuevos datos:");

            System.out.println("ID Entrenador: ");
            int idEntrenador = scanner.nextInt();

            System.out.println("ID Sala: ");
            int idSala = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Nivel: ");
            String nivel = scanner.nextLine();

            System.out.println("Duración (en minutos): ");
            int duracion = scanner.nextInt();

            clase.setIdEntrenador(idEntrenador);
            clase.setIdSala(idSala);
            clase.setNombre(nombre);
            clase.setNivel(nivel);
            clase.setDuracionMinutos(duracion);

            claseService.actualizar(clase);
            System.out.println("Clase actualizada correctamente");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        scanner.nextLine();

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            int resultado = claseService.eliminar(id);
            if (resultado > 0) {
                System.out.println("Clase eliminada correctamente");
            } else {
                System.out.println("No se puede eliminar la clase");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}