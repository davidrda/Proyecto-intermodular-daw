package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Clase {

    private int idClase, idEntrenador, idSala, duracionMinutos;
    private String nombre, nivel;

    public Clase() {
    }

    public Clase(int idClase, int idEntrenador, int idSala,String nombre, String nivel, int duracionMinutos) {
        this.idClase = idClase;
        this.idEntrenador = idEntrenador;
        this.idSala = idSala;
        this.duracionMinutos = duracionMinutos;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public Clase(int idEntrenador, int idSala, String nombre, String nivel, int duracionMinutos) {
        this.idEntrenador = idEntrenador;
        this.idSala = idSala;
        this.duracionMinutos = duracionMinutos;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "[" + idClase + "] " + nombre +
                " | Nivel: " + nivel +
                " | Duración: " + duracionMinutos + " min" +
                " | Entrenador ID: " + idEntrenador +
                " | Sala ID: " + idSala;
    }
}
