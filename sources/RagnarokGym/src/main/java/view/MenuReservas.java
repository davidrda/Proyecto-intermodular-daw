package view;

import model.Reserva;
import service.ReservaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuReservas extends MenuBase{

    private ReservaService reservaService;

    public MenuReservas() {
        super();
        reservaService = new ReservaService();
    }

    @Override
    protected String getTextoMenu() {
        return """
            === MENÚ RESERVAS ===
            | 0. Salir
            | 1. Insertar
            | 2. Listar todas
            | 3. Listar detalladas
            | 4. Buscar por ID
            | 5. Actualizar
            | 6. Eliminar
            
            Elige una opción:
            """;
    }

    @Override
    protected void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> insertar();
            case 2 -> listarTodos();
            case 3 -> listarDetalladas();
            case 4 -> buscarPorId();
            case 5 -> actualizar();
            case 6 -> eliminar();
            case 0 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción no válida");
        }
    }

    @Override
    protected void insertar() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el ID del cliente: ");
        int idCliente = scanner.nextInt();

        System.out.println("Inserta el ID del horario: ");
        int idHorario = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Inserta el estado: ");
        String estado = scanner.nextLine();

        LocalDate fechaReserva = LocalDate.now();

        Reserva reserva = new Reserva(idCliente, idHorario, fechaReserva, estado);

        try {
            reservaService.insertar(reserva);
            System.out.println("Reserva insertada correctamente");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void listarTodos() {
        List<Reserva> reservas = reservaService.listarTodos();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas en la lista");
        } else {
            for (Reserva r : reservas) {
                System.out.println(r);
            }
        }
    }

    private void listarDetalladas() {
        List<String> reservas = reservaService.listarReservasDetalladas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas en la lista");
        } else {
            for (String r : reservas) {
                System.out.println(r);
            }
        }
    }

    @Override
    protected void buscarPorId() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            Reserva reserva = reservaService.buscarPorId(id);
            System.out.println(reserva);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void actualizar() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el ID de la reserva a actualizar: ");
        int id = scanner.nextInt();

        try {
            Reserva reserva = reservaService.buscarPorId(id);

            System.out.println("Reserva actual: " + reserva);
            System.out.println("Introduce los nuevos datos:");

            System.out.println("ID Cliente: ");
            int idCliente = scanner.nextInt();

            System.out.println("ID Horario: ");
            int idHorario = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Estado: ");
            String estado = scanner.nextLine();

            reserva.setIdCliente(idCliente);
            reserva.setIdHorario(idHorario);
            reserva.setEstado(estado);

            reservaService.actualizar(reserva);
            System.out.println("Reserva actualizada correctamente");

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
            int resultado = reservaService.eliminar(id);
            if (resultado > 0) {
                System.out.println("Reserva eliminada correctamente");
            } else {
                System.out.println("No se puede eliminar la reserva");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}