package view;

import java.util.Scanner;

public class MenuPrincipal {

    public void mostrar(){
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("""
                === MENÚ PRINCIPAL ===
                | 0. Salir
                | 1. Clases
                | 2. Clientes
                | 3. Pagos
                | 4. Reservas
                | 5. Entrenadores
                
                Elige que menú quieres abrir:
                """);
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> abrirMenuClases();
                case 2 -> abrirMenuClientes();
                case 3 -> abrirMenuPagos();
                case 4 -> abrirMenuReservas();
                case 5 -> abrirMenuEntrenadores();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    private void abrirMenuClases(){
        MenuClases menuClases = new MenuClases();
        menuClases.mostrar();
    }

    private void abrirMenuClientes(){
        MenuClientes menuClientes = new MenuClientes();
        menuClientes.mostrar();
    }


    private void abrirMenuPagos(){
        MenuPagos menuPagos = new MenuPagos();
        menuPagos.mostrar();
    }

    private void abrirMenuReservas(){
        MenuReservas menuReservas = new MenuReservas();
        menuReservas.mostrar();
    }

    private void abrirMenuEntrenadores(){
        MenuEntrenadores menuEntrenador = new MenuEntrenadores();
        menuEntrenador.mostrar();
    }
}
