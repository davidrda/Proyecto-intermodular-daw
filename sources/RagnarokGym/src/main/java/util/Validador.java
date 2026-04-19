package util;

public class Validador {

    public static void validarNoVacio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
    }

    public static void validarSinNumeros(String valor, String campo) {
        if (valor.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El campo " + campo + " no puede contener números");
        }
    }

    public static void validarSinLetras(String valor, String campo) {
        if (valor.matches(".*\\p{L}.*")) {
            throw new IllegalArgumentException("El campo " + campo + " no puede contener letras");
        }
    }
}
