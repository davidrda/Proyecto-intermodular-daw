package service;

import dao.EntrenadorDAO;
import model.Entrenador;

import java.util.List;

public class EntrenadorService {

    private EntrenadorDAO entrenadorDAO;

    public EntrenadorService() {
        entrenadorDAO = new EntrenadorDAO();
    }

    public int insertar(Entrenador entrenador) {
        validarEntrenador(entrenador);
        return entrenadorDAO.insertar(entrenador);
    }

    public List<Entrenador> listarTodos() {
        return entrenadorDAO.listarTodos();
    }

    public Entrenador buscarPorId(int id) {
        Entrenador entrenador = entrenadorDAO.buscarPorId(id);
        if (entrenador == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return entrenador;
    }

    public int actualizar(Entrenador entrenador) {
        validarEntrenador(entrenador);
        return entrenadorDAO.actualizar(entrenador);
    }

    public int eliminar(int id) {
        Entrenador entrenador = entrenadorDAO.buscarPorId(id);
        if (entrenador == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return entrenadorDAO.eliminar(id);
    }

    // VALIDACIONES
    private void validarEntrenador(Entrenador entrenador) {
        validarNoVacio(entrenador.getNombre(), "nombre");
        validarNoVacio(entrenador.getApellidos(), "apellidos");
        validarNoVacio(entrenador.getDni(), "DNI");
        validarNoVacio(entrenador.getEmail(), "email");
        validarNoVacio(entrenador.getTelefono(), "teléfono");
        validarNoVacio(entrenador.getEspecialidad(), "especialidad");

        validarSinNumeros(entrenador.getNombre(), "nombre");
        validarSinNumeros(entrenador.getApellidos(), "apellidos");

        validarSinLetras(entrenador.getTelefono(), "teléfono");
    }

    private void validarNoVacio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
    }

    private void validarSinNumeros(String valor, String campo) {
        if (valor.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El campo " + campo + " no puede contener números");
        }
    }

    private void validarSinLetras(String valor, String campo) {
        if (valor.matches(".*\\p{L}.*")) {
            throw new IllegalArgumentException("El campo " + campo + " no puede contener letras");
        }
    }
}