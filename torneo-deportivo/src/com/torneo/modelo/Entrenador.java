package com.torneo.modelo;

/**
 * Entrenador hereda de Persona.
 * Tiene asociacion bidireccional con Equipo (1 Equipo a 0..* Entrenador,
 * en este diseno un entrenador conoce a su equipo y viceversa).
 */
public class Entrenador extends Persona {

    private int aniosExperiencia;
    private Equipo equipo; // lado inverso de la asociacion bidireccional

    public Entrenador(String nombre, String documento, int edad, int aniosExperiencia) {
        super(nombre, documento, edad);
        setAniosExperiencia(aniosExperiencia);
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        if (aniosExperiencia >= 0) {
            this.aniosExperiencia = aniosExperiencia;
        } else {
            this.aniosExperiencia = 0;
        }
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public String mostrarRol() {
        return "Entrenador";
    }

    @Override
    public String toString() {
        String nombreEquipo = (equipo != null) ? equipo.getNombre() : "Sin equipo asignado";
        return String.format("[%s] %s | Doc: %s | Edad: %d | Experiencia: %d anios | Equipo: %s",
                mostrarRol(), nombre, documento, edad, aniosExperiencia, nombreEquipo);
    }
}
