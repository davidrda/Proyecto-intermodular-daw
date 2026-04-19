package service;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService() {
        clienteDAO = new ClienteDAO();
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

    public int eliminar(int id){
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos");
        }
        return clienteDAO.eliminar(id);
    }

    // VALIDACIONES
    private void validarCliente(Cliente cliente){
        validarNoVacio(cliente.getNombre(), "nombre");
        validarNoVacio(cliente.getApellidos(), "apellidos");
        validarNoVacio(cliente.getDni(), "DNI");
        validarNoVacio(cliente.getEmail(), "email");
        validarNoVacio(cliente.getTelefono(), "telefono");

        validarSinNumeros(cliente.getNombre(), "nombre");
        validarSinNumeros(cliente.getApellidos(), "apellidos");

        validarSinLetra(cliente.getTelefono(), "telefono");

    }

    private void validarNoVacio (String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
    }

    private void validarSinNumeros(String valor, String campo) {
        if (valor.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El campo " + campo + " no puede contener números");
        }
    }

    private void validarSinLetra(String valor, String campo) {
        if (valor.matches(".*\\p{L}.*")) {
            throw new IllegalArgumentException("El campo " + campo + " no puede contener letras");
        }
    }

    private void validarDniUnico(String dni){
        if (clienteDAO.buscarPorDni(dni) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese DNI");
        }
    }
}
