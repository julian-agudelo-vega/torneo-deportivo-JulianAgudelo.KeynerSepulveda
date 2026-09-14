package com.torneo.modelo;

/**
 * Clase abstracta que representa a cualquier persona dentro del torneo.
 * De aqui heredan Jugador y Entrenador (herencia + polimorfismo).
 */
public abstract class Persona {

    protected String nombre;
    protected String documento;
    protected int edad;

    public Persona(String nombre, String documento, int edad) {
        setNombre(nombre);
        this.documento = documento;
        setEdad(edad);
    }

    // ---- Encapsulamiento: getters/setters con validacion ----

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        } else {
            this.nombre = "Sin nombre";
        }
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad > 0 && edad < 100) {
            this.edad = edad;
        } else {
            this.edad = 0; // valor por defecto si es invalida
        }
    }

    /**
     * Metodo abstracto: cada subclase decide como mostrar su rol.
     * Punto clave de polimorfismo del reto.
     */
    public abstract String mostrarRol();

    @Override
    public String toString() {
        return nombre + " (" + documento + ", " + edad + " anios)";
    }
}
