package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Reserva {

    private int idReserva, idCliente, idHorario;
    private LocalDate fechaReserva;
    private String estado;

    public Reserva() {
    }

    public Reserva(int idReserva, int idCliente, int idHorario, LocalDate fechaReserva, String estado) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idHorario = idHorario;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }

    public Reserva(int idCliente, int idHorario, LocalDate fechaReserva, String estado) {
        this.idCliente = idCliente;
        this.idHorario = idHorario;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "[" + idReserva + "] " +
                "Cliente ID: " + idCliente +
                " | Horario ID: " + idHorario +
                " | Fecha: " + fechaReserva +
                " | Estado: " + estado;
    }
}