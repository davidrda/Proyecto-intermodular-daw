package view;

import model.Entrenador;
import service.EntrenadorService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuEntrenadores extends MenuBase{

    private EntrenadorService entrenadorService;

    public MenuEntrenadores() {
        super();
        entrenadorService = new EntrenadorService();
    }

    @Override
    protected String getTextoMenu() {
        return """
            === MENÚ ENTRENADORES ===
            | 0. Salir
            | 1. Insertar
            | 2. Listar todos
            | 3. Buscar por ID
            | 4. Actualizar
            | 5. Eliminar
            
            Elige una opción:
            """;
    }

    @Override
    protected void insertar() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("Inserta los apellidos: ");
        String apellidos = scanner.nextLine();

        System.out.println("Inserta el DNI: ");
        String dni = scanner.nextLine();

        System.out.println("Inserta el email: ");
        String email = scanner.nextLine();

        System.out.println("Inserta el teléfono: ");
        String telefono = scanner.nextLine();

        System.out.println("Inserta la especialidad: ");
        String especialidad = scanner.nextLine();

        LocalDate fechaContratacion = LocalDate.now();

        Entrenador entrenador = new Entrenador(nombre, apellidos, dni, email, telefono, especialidad, fechaContratacion, null);

        try {
            entrenadorService.insertar(entrenador);
            System.out.println("Entrenador insertado correctamente");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void listarTodos() {
        List<Entrenador> entrenadores = entrenadorService.listarTodos();
        if (entrenadores.isEmpty()) {
            System.out.println("No hay entrenadores en la lista");
        } else {
            for (Entrenador e : entrenadores) {
                System.out.println(e);
            }
        }
    }

    @Override
    protected void buscarPorId() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            Entrenador entrenador = entrenadorService.buscarPorId(id);
            System.out.println(entrenador);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void actualizar() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el ID del entrenador a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Entrenador entrenador = entrenadorService.buscarPorId(id);

            System.out.println("Entrenador actual: " + entrenador);
            System.out.println("Introduce los nuevos datos:");

            System.out.println("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Apellidos: ");
            String apellidos = scanner.nextLine();

            System.out.println("DNI: ");
            String dni = scanner.nextLine();

            System.out.println("Email: ");
            String email = scanner.nextLine();

            System.out.println("Teléfono: ");
            String telefono = scanner.nextLine();

            System.out.println("Especialidad: ");
            String especialidad = scanner.nextLine();

            entrenador.setNombre(nombre);
            entrenador.setApellidos(apellidos);
            entrenador.setDni(dni);
            entrenador.setEmail(email);
            entrenador.setTelefono(telefono);
            entrenador.setEspecialidad(especialidad);

            entrenadorService.actualizar(entrenador);
            System.out.println("Entrenador actualizado correctamente");

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
            int resultado = entrenadorService.eliminar(id);
            if (resultado > 0) {
                System.out.println("Entrenador eliminado correctamente");
            } else {
                System.out.println("No se puede eliminar el entrenador");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}