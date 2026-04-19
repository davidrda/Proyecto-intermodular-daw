package service;

import dao.ClaseDAO;
import model.Clase;

import java.util.List;

public class ClaseService {

    private ClaseDAO claseDAO;

    public ClaseService() {
        claseDAO = new ClaseDAO();
    }

    public int insertar(Clase clase) {
        validarClase(clase);
        return claseDAO.insertar(clase);
    }

    public List<Clase> listarTodos() {
        return claseDAO.listarTodos();
    }

    public Clase buscarPorId(int id) {
        Clase clase = claseDAO.buscarPorId(id);
        if (clase == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return clase;
    }

    public int actualizar(Clase clase) {
        validarClase(clase);
        return claseDAO.actualizar(clase);
    }

    public int eliminar(int id) {
        Clase clase = claseDAO.buscarPorId(id);
        if (clase == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return claseDAO.eliminar(id);
    }

    // VALIDACIONES
    private void validarClase(Clase clase) {
        validarNoVacio(clase.getNombre(), "nombre");
        validarNoVacio(clase.getNivel(), "nivel");

        if (clase.getDuracionMinutos() <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que 0");
        }

        if (clase.getIdEntrenador() <= 0) {
            throw new IllegalArgumentException("El id del entrenador no es válido");
        }

        if (clase.getIdSala() <= 0) {
            throw new IllegalArgumentException("El id de la sala no es válido");
        }
    }

    private void validarNoVacio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
    }
}