import dao.ClienteDAO;
import model.Cliente;
import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    static void main() {

        ClienteDAO clienteDAO = new ClienteDAO();

        for (Cliente c : clienteDAO.listarTodos()) {
            System.out.println(c);
        }

        System.out.println();

        clienteDAO.eliminar(6);

        for (Cliente c : clienteDAO.listarTodos()) {
            System.out.println(c);
        }

    }
}
