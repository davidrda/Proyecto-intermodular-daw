package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Pago {

    private int idPago, idServicio, idCliente;
    private String metodoPago;
    private LocalDate fechaPago;
    private double importe;
    private String estado;

    public Pago() {
    }

    public Pago(int idPago, int idServicio, int idCliente, String metodoPago, LocalDate fechaPago, double importe, String estado) {
        this.idPago = idPago;
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.importe = importe;
        this.estado = estado;
    }

    public Pago(int idServicio, int idCliente, String metodoPago, LocalDate fechaPago, double importe, String estado) {
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.importe = importe;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "[" + idPago + "] " +
                "Servicio ID: " + idServicio +
                " | Cliente ID: " + idCliente +
                " | Método: " + metodoPago +
                " | Fecha: " + fechaPago +
                " | Importe: " + importe +
                " | Estado: " + estado;
    }
}
