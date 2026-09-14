package com.torneo.modelo;

/**
 * Jugador hereda de Persona.
 * Tiene una asociacion unidireccional hacia Posicion (* a 1).
 */
public class Jugador extends Persona {

    private Posicion posicion;      // asociacion unidireccional Jugador -> Posicion
    private int numeroCamiseta;
    private double calificacion;    // atributo extra: demuestra conversion/validacion con double

    public Jugador(String nombre, String documento, int edad, Posicion posicion, int numeroCamiseta) {
        super(nombre, documento, edad);
        this.posicion = posicion;
        setNumeroCamiseta(numeroCamiseta);
        this.calificacion = 0.0;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    public void setNumeroCamiseta(int numeroCamiseta) {
        if (numeroCamiseta > 0 && numeroCamiseta <= 99) {
            this.numeroCamiseta = numeroCamiseta;
        } else {
            this.numeroCamiseta = 0;
        }
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        if (calificacion >= 0.0 && calificacion <= 10.0) {
            this.calificacion = calificacion;
        }
    }

    @Override
    public String mostrarRol() {
        return "Jugador";
    }

    @Override
    public String toString() {
        String nombrePosicion = (posicion != null) ? posicion.getNombre() : "N/A";
        return String.format("[%s] %s | Doc: %s | Edad: %d | Posicion: %s | #%d | Calificacion: %.1f",
                mostrarRol(), nombre, documento, edad, nombrePosicion, numeroCamiseta, calificacion);
    }
}
