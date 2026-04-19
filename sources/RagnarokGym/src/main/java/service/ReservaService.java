package service;

import dao.ReservaDAO;
import model.Reserva;

import java.time.LocalDate;
import java.util.List;

public class ReservaService {

    private ReservaDAO reservaDAO;

    public ReservaService() {
        reservaDAO = new ReservaDAO();
    }

    public int insertar(Reserva reserva) {
        validarReserva(reserva);
        return reservaDAO.insertar(reserva);
    }

    public List<Reserva> listarTodos() {
        return reservaDAO.listarTodos();
    }

    public Reserva buscarPorId(int id) {
        Reserva reserva = reservaDAO.buscarPorId(id);
        if (reserva == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return reserva;
    }

    public int actualizar(Reserva reserva) {
        validarReserva(reserva);
        return reservaDAO.actualizar(reserva);
    }

    public int eliminar(int id) {
        Reserva reserva = reservaDAO.buscarPorId(id);
        if (reserva == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return reservaDAO.eliminar(id);
    }

    public List<String> listarReservasDetalladas() {
        return reservaDAO.listarReservasDetalladas();
    }

    // VALIDACIONES
    private void validarReserva(Reserva reserva) {
        if (reserva.getIdCliente() <= 0) {
            throw new IllegalArgumentException("El id del cliente no es válido");
        }

        if (reserva.getIdHorario() <= 0) {
            throw new IllegalArgumentException("El id del horario no es válido");
        }

        if (reserva.getFechaReserva() == null) {
            throw new IllegalArgumentException("La fecha de reserva no puede estar vacía");
        }

        if (reserva.getFechaReserva().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de reserva no puede ser anterior a hoy");
        }

        validarNoVacio(reserva.getEstado(), "estado");
    }

    private void validarNoVacio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
    }
}