package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Cliente {

    private int idCliente;
    private String nombre, apellidos, dni, email, telefono;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String apellidos, String dni, String email, String telefono, LocalDate fechaAlta, LocalDate fechaBaja) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    public Cliente(String nombre, String apellidos, String dni, String email, String telefono, LocalDate fechaAlta, LocalDate fechaBaja) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    @Override
    public String toString() {
        return String.format(
                "[%d] %s %s | DNI: %s | Email: %s | Tel: %s | Alta: %s | Baja: %s",
                idCliente,
                nombre,
                apellidos,
                dni,
                email,
                telefono,
                fechaAlta,
                fechaBaja != null ? fechaBaja : "Activo"
        );
    }
}
