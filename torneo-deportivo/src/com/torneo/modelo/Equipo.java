package com.torneo.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Equipo.
 * - Agregacion con Jugador (0..1 Equipo a 0..* Jugador): si el equipo
 *   desaparece, los jugadores podrian seguir existiendo en otro contexto.
 * - Asociacion bidireccional con Entrenador (1 a 0..*).
 */
public class Equipo implements Notificable {

    private String nombre;
    private List<Jugador> jugadores;   // agregacion
    private Entrenador entrenador;     // asociacion bidireccional

    public Equipo(String nombre) {
        if (!esNombreValido(nombre)) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacio.");
        }
        this.nombre = nombre.trim();
        this.jugadores = new ArrayList<>();
    }

    /** Metodo estatico de validacion requerido por el reto. */
    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (esNombreValido(nombre)) {
            this.nombre = nombre.trim();
        }
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
        if (entrenador != null) {
            entrenador.setEquipo(this); // mantiene la bidireccionalidad
        }
    }

    // ---- Sobrecarga de metodos (extension opcional) ----

    public void registrar(Jugador jugador) {
        if (jugador != null) {
            jugadores.add(jugador);
        }
    }

    public void registrar(Entrenador nuevoEntrenador) {
        setEntrenador(nuevoEntrenador);
    }

    @Override
    public void notificar(String mensaje) {
        System.out.println(Notificable.formatoEstandar("[" + nombre + "] " + mensaje));
    }

    @Override
    public String toString() {
        return nombre;
    }
}
