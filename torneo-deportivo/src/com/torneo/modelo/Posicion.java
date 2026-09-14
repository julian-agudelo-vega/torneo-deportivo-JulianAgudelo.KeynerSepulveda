package com.torneo.modelo;

/**
 * Representa la posicion de un jugador (Delantero, Portero, etc.).
 * Relacion con Jugador: asociacion unidireccional (* Jugador -> 1 Posicion).
 */
public class Posicion {

    private String nombre;

    public Posicion(String nombre) {
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        } else {
            this.nombre = "Sin definir";
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
