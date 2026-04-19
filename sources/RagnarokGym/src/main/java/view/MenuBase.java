package view;

import java.util.Scanner;

public abstract class MenuBase {

    protected Scanner scanner;

    public MenuBase() {
        scanner = new Scanner(System.in);
    }

    public void mostrar() {
        int opcion = 0;
        do {
            System.out.println(getTextoMenu());
            opcion = scanner.nextInt();
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    protected void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> insertar();
            case 2 -> listarTodos();
            case 3 -> buscarPorId();
            case 4 -> actualizar();
            case 5 -> eliminar();
            case 0 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción no válida");
        }
    }

    protected abstract String getTextoMenu();
    protected abstract void insertar();
    protected abstract void listarTodos();
    protected abstract void buscarPorId();
    protected abstract void actualizar();
    protected abstract void eliminar();
}