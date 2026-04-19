package service;

import dao.EntrenadorDAO;
import model.Entrenador;
import util.Validador;

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
        Validador.validarNoVacio(entrenador.getNombre(), "nombre");
        Validador.validarNoVacio(entrenador.getApellidos(), "apellidos");
        Validador.validarNoVacio(entrenador.getDni(), "DNI");
        Validador.validarNoVacio(entrenador.getEmail(), "email");
        Validador.validarNoVacio(entrenador.getTelefono(), "teléfono");
        Validador.validarNoVacio(entrenador.getEspecialidad(), "especialidad");

        Validador.validarSinNumeros(entrenador.getNombre(), "nombre");
        Validador.validarSinNumeros(entrenador.getApellidos(), "apellidos");

        Validador.validarSinLetras(entrenador.getTelefono(), "teléfono");
    }

}