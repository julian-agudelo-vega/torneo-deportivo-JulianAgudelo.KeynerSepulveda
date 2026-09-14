package com.torneo.modelo;

/**
 * Partido: asociacion con Equipo (1 partido a 2 equipos).
 * Valida que los goles registrados no sean negativos.
 */
public class Partido {

    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private String fecha;
    private boolean jugado;

    public Partido(Equipo equipoLocal, Equipo equipoVisitante, String fecha) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fecha = fecha;
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.jugado = false;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public String getFecha() {
        return fecha;
    }

    public boolean isJugado() {
        return jugado;
    }

    /**
     * Registra el resultado validando que ningun marcador sea negativo.
     * @return true si el resultado fue valido y se registro, false si no.
     */
    public boolean registrarResultado(int golesLocal, int golesVisitante) {
        if (golesLocal < 0 || golesVisitante < 0) {
            return false;
        }
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.jugado = true;
        return true;
    }

    @Override
    public String toString() {
        String marcador = jugado ? (golesLocal + " - " + golesVisitante) : "pendiente";
        return equipoLocal.getNombre() + " vs " + equipoVisitante.getNombre()
                + " (" + fecha + ") | " + marcador;
    }
}
