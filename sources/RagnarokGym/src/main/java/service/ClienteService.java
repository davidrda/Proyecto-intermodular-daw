package service;

import dao.ClienteDAO;
import dao.PagoDAO;
import model.Cliente;
import model.Pago;
import model.Reserva;
import util.Validador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO;
    private PagoService pagoService;
    private ReservaService reservaService;

    public ClienteService() {
        clienteDAO = new ClienteDAO();
        pagoService = new PagoService();
        reservaService = new ReservaService();
    }

    public int insertar(Cliente cliente) {
        validarCliente(cliente);
        validarDniUnico(cliente.getDni());
        return clienteDAO.insertar(cliente);
    }

    public List<Cliente> listarTodos(){
        return clienteDAO.listarTodos();
    }

    public Cliente buscarPorId(int id){
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return cliente;
    }

    public int actualizar(Cliente cliente) {
        validarCliente(cliente);
        return clienteDAO.actualizar(cliente);
    }

    public int eliminar(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos");
        }
        if (tienePagos(id)) {
            throw new IllegalArgumentException("No se puede eliminar un cliente con pagos registrados. Usa la opción de dar de baja.");
        }
        if (tieneReservas(id)) {
            throw new IllegalArgumentException("No se puede eliminar un cliente con reservas registradas. Elimina su reserva.");
        }
        return clienteDAO.eliminar(id);
    }

    public int darDeBaja(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos");
        }
        cliente.setFechaBaja(LocalDate.now());
        return clienteDAO.actualizar(cliente);
    }


    // VALIDACIONES
    private void validarCliente(Cliente cliente){
        Validador.validarNoVacio(cliente.getNombre(), "nombre");
        Validador.validarNoVacio(cliente.getApellidos(), "apellidos");
        Validador.validarNoVacio(cliente.getDni(), "DNI");
        Validador.validarNoVacio(cliente.getEmail(), "email");
        Validador.validarNoVacio(cliente.getTelefono(), "telefono");

        Validador.validarSinNumeros(cliente.getNombre(), "nombre");
        Validador.validarSinNumeros(cliente.getApellidos(), "apellidos");

        Validador.validarSinLetras(cliente.getTelefono(), "telefono");

    }


    private void validarDniUnico(String dni){
        if (clienteDAO.buscarPorDni(dni) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese DNI");
        }
    }

    private boolean tienePagos(int idCliente){
        for (Pago p : pagoService.listarTodos()) {
            if (p.getIdCliente() == idCliente) {
                return true;
            }
        }
        return false;
    }

    private boolean tieneReservas(int idCliente){
        for (Reserva r : reservaService.listarTodos()) {
            if (r.getIdCliente() == idCliente) {
                return true;
            }
        }
        return false;
    }
}
