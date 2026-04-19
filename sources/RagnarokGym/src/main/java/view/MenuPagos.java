package view;

import model.Pago;
import service.PagoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuPagos {

    private PagoService pagoService;
    private Scanner scanner;

    public MenuPagos() {
        pagoService = new PagoService();
        scanner = new Scanner(System.in);
    }

    public void mostrar() {

        int opcion = 0;

        do {
            System.out.println("""
                === MENÚ PAGOS ===
                | 0. Salir
                | 1. Insertar
                | 2. Listar todos
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

        System.out.println("Inserta el ID del servicio: ");
        int idServicio = scanner.nextInt();

        System.out.println("Inserta el ID del cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Inserta el método de pago: ");
        String metodoPago = scanner.nextLine();

        System.out.println("Inserta el importe: ");
        double importe = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Inserta el estado: ");
        String estado = scanner.nextLine();

        LocalDate fechaPago = LocalDate.now();

        Pago pago = new Pago(idServicio, idCliente, metodoPago, fechaPago, importe, estado);

        try {
            pagoService.insertar(pago);
            System.out.println("Pago insertado correctamente");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarTodos() {
        List<Pago> pagos = pagoService.listarTodos();
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos en la lista");
        } else {
            for (Pago p : pagos) {
                System.out.println(p);
            }
        }
    }

    private void buscarPorId() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            Pago pago = pagoService.buscarPorId(id);
            System.out.println(pago);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void actualizar() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el ID del pago a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Pago pago = pagoService.buscarPorId(id);

            System.out.println("Pago actual: " + pago);
            System.out.println("Introduce los nuevos datos:");

            System.out.println("ID Servicio: ");
            int idServicio = scanner.nextInt();

            System.out.println("ID Cliente: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Método de pago: ");
            String metodoPago = scanner.nextLine();

            System.out.println("Importe: ");
            double importe = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Estado: ");
            String estado = scanner.nextLine();

            pago.setIdServicio(idServicio);
            pago.setIdCliente(idCliente);
            pago.setMetodoPago(metodoPago);
            pago.setImporte(importe);
            pago.setEstado(estado);

            pagoService.actualizar(pago);
            System.out.println("Pago actualizado correctamente");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        scanner.nextLine();

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            int resultado = pagoService.eliminar(id);
            if (resultado > 0) {
                System.out.println("Pago eliminado correctamente");
            } else {
                System.out.println("No se puede eliminar el pago");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}