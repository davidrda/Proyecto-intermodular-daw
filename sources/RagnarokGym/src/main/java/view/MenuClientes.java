package view;

import model.Cliente;
import service.ClienteService;

import java.time.LocalDate;
import java.util.List;

public class MenuClientes extends MenuBase{

    private ClienteService clienteService;

    public MenuClientes() {
        super();
        clienteService = new ClienteService();
    }

    @Override
    protected String getTextoMenu() {
        return """
        === MENÚ CLIENTES ===
        | 0. Salir
        | 1. Insertar
        | 2. Listar todos
        | 3. Buscar por ID
        | 4. Actualizar
        | 5. Eliminar
        | 6. Dar de baja
        
        Elige una opción:
        """;
    }

    @Override
    protected void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> insertar();
            case 2 -> listarTodos();
            case 3 -> buscarPorId();
            case 4 -> actualizar();
            case 5 -> eliminar();
            case 6 -> darDeBaja();
            case 0 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción no válida");
        }
    }

    @Override
    protected void insertar() {
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("Inserta el apellido");
        String apellido = scanner.nextLine();

        System.out.println("Inserta el dni");
        String dni = scanner.nextLine();

        System.out.println("Inserta el email");
        String email = scanner.nextLine();

        System.out.println("Inserta el teléfono");
        String telefono = scanner.nextLine();

        LocalDate fechaAlta = LocalDate.now();

        Cliente cliente = new Cliente(nombre, apellido, dni, email, telefono, fechaAlta, null);


        try {
            clienteService.insertar(cliente);
            System.out.println("Cliente insertado correctamente");
        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void listarTodos(){
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()){
            System.out.println("No hay clientes en la lista");
        } else {
            for (Cliente c: clientes){
                System.out.println(c);
            }
        }
    }

    @Override
    protected void buscarPorId(){
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            Cliente cliente = clienteService.buscarPorId(id);
            System.out.println(cliente);
        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void actualizar(){
        scanner.nextLine(); // Limpia el buffer

        System.out.println("Inserta el ID del cliente a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Cliente cliente = clienteService.buscarPorId(id);

            System.out.println("Cliente actual: " + cliente);
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

            cliente.setNombre(nombre);
            cliente.setApellidos(apellidos);
            cliente.setDni(dni);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);

            clienteService.actualizar(cliente);
            System.out.println("Cliente actualizado correctamente");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void eliminar(){
        scanner.nextLine();

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            int resultado = clienteService.eliminar(id);
            if (resultado > 0) {
                System.out.println("Cliente eliminado correctamente");
            } else {
                System.out.println("No se puede eliminar el cliente");
            }
        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void darDeBaja(){
        scanner.nextLine();

        System.out.println("Introduce el id: ");
        int id = scanner.nextInt();

        try {
            clienteService.darDeBaja(id);
            System.out.println("Cliente dado de baja correctamente");
        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
