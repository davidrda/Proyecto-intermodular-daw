package service;

import dao.PagoDAO;
import model.Pago;

import java.util.List;

public class PagoService {

    private PagoDAO pagoDAO;

    public PagoService() {
        pagoDAO = new PagoDAO();
    }

    public int insertar(Pago pago) {
        validarPago(pago);
        return pagoDAO.insertar(pago);
    }

    public List<Pago> listarTodos() {
        return pagoDAO.listarTodos();
    }

    public Pago buscarPorId(int id) {
        Pago pago = pagoDAO.buscarPorId(id);
        if (pago == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return pago;
    }

    public int actualizar(Pago pago) {
        validarPago(pago);
        return pagoDAO.actualizar(pago);
    }

    public int eliminar(int id) {
        Pago pago = pagoDAO.buscarPorId(id);
        if (pago == null) {
            throw new IllegalArgumentException("El id que has introducido no existe en la base de datos.");
        }
        return pagoDAO.eliminar(id);
    }

    // VALIDACIONES
    private void validarPago(Pago pago) {
        if (pago.getIdServicio() <= 0) {
            throw new IllegalArgumentException("El id del servicio no es válido");
        }

        if (pago.getIdCliente() <= 0) {
            throw new IllegalArgumentException("El id del cliente no es válido");
        }

        validarNoVacio(pago.getMetodoPago(), "método de pago");
        validarNoVacio(pago.getEstado(), "estado");

        if (pago.getFechaPago() == null) {
            throw new IllegalArgumentException("La fecha de pago no puede estar vacía");
        }

        if (pago.getImporte() <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor que 0");
        }
    }

    private void validarNoVacio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
    }
}