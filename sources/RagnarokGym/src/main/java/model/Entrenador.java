package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Entrenador {

    private int idEntrenador;
    private String nombre, apellidos, dni, email, telefono, especialidad;
    private LocalDate fechaContratacion, fechaBaja;

    public Entrenador() {
    }

    public Entrenador(int idEntrenador, String nombre, String apellidos, String dni, String email, String telefono, String especialidad, LocalDate fechaContratacion, LocalDate fechaBaja) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.fechaContratacion = fechaContratacion;
        this.fechaBaja = fechaBaja;
    }

    public Entrenador(String nombre, String apellidos, String dni, String email, String telefono, String especialidad, LocalDate fechaContratacion, LocalDate fechaBaja) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.fechaContratacion = fechaContratacion;
        this.fechaBaja = fechaBaja;
    }

    @Override
    public String toString() {
        String baja = (fechaBaja != null) ? fechaBaja.toString() : "Activo";

        return "[" + idEntrenador + "] " + nombre + " " + apellidos +
                " | DNI: " + dni +
                " | Email: " + email +
                " | Tel: " + telefono +
                " | Especialidad: " + especialidad +
                " | Contratación: " + fechaContratacion +
                " | Baja: " + baja;
    }
}
