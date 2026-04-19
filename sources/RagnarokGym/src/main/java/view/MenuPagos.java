package view;

import model.Pago;
import service.PagoService;

import java.time.LocalDate;
import java.util.List;

public class MenuPagos extends MenuBase{

    private PagoService pagoService;

    public MenuPagos() {
        super();
        pagoService = new PagoService();
    }

    @Override
    protected String getTextoMenu() {
        return """
            === MENÚ PAGOS ===
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

    @Override
    protected void listarTodos() {
        List<Pago> pagos = pagoService.listarTodos();
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos en la lista");
        } else {
            for (Pago p : pagos) {
                System.out.println(p);
            }
        }
    }

    @Override
    protected void buscarPorId() {
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

    @Override
    protected void actualizar() {
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

    @Override
    protected void eliminar() {
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