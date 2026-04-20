package view;

import model.Clase;
import service.ClaseService;

import java.util.List;

public class MenuClases extends MenuBase{

    private ClaseService claseService;

    public MenuClases() {
        super();
        claseService = new ClaseService();
    }

    @Override
    protected String getTextoMenu() {
        return """
            === MENÚ CLASES ===
            | 0. Salir
            | 1. Insertar
            | 2. Listar todas
            | 3. Buscar por ID
            | 4. Actualizar
            | 5. Eliminar
            
            Elige una opción:
            """;
    }

    @Override
    protected void insertar() {
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

    @Override
    protected void listarTodos() {
        List<Clase> clases = claseService.listarTodos();
        if (clases.isEmpty()) {
            System.out.println("No hay clases en la lista");
        } else {
            for (Clase c : clases) {
                System.out.println(c);
            }
        }
    }

    @Override
    protected void buscarPorId() {
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

    @Override
    protected void actualizar() {
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

    @Override
    protected void eliminar() {
        scanner.nextLine();

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            claseService.eliminar(id);
            System.out.println("Clase eliminada correctamente");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}