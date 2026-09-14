package com.torneo.modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Torneo.
 * - Composicion con Equipo (1 a 0..*): si el torneo se destruye, sus
 *   equipos (en este modelo) tambien dejan de existir.
 * - Composicion con Partido (1 a 0..*): los partidos solo tienen sentido
 *   dentro de este torneo.
 */
public class Torneo {

    private String nombre;
    private List<Equipo> equipos;   // composicion
    private List<Partido> partidos; // composicion

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.equipos = new ArrayList<>();
        this.partidos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    /** Registra un equipo validando que el nombre no este repetido. */
    public boolean registrarEquipo(Equipo equipo) {
        if (equipo == null) {
            return false;
        }
        for (Equipo e : equipos) {
            if (e.getNombre().equalsIgnoreCase(equipo.getNombre())) {
                return false; // nombre repetido
            }
        }
        equipos.add(equipo);
        return true;
    }

    /** Busca un equipo por nombre (usa equalsIgnoreCase). */
    public Equipo buscarEquipoPorNombre(String nombre) {
        for (Equipo e : equipos) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }

    public void programarPartido(Partido partido) {
        partidos.add(partido);
    }

    /**
     * Calcula la tabla de posiciones: equipo, partidos jugados, puntos.
     * 3 puntos por victoria, 1 por empate, 0 por derrota.
     * Devuelve la lista ya ordenada de mayor a menor puntaje.
     */
    public List<String[]> calcularTablaPosiciones() {
        Map<String, Integer> puntos = new LinkedHashMap<>();
        Map<String, Integer> jugados = new LinkedHashMap<>();

        for (Equipo e : equipos) {
            puntos.put(e.getNombre(), 0);
            jugados.put(e.getNombre(), 0);
        }

        for (Partido p : partidos) {
            if (!p.isJugado()) {
                continue; // continue: se omiten los partidos que aun no tienen resultado
            }

            String local = p.getEquipoLocal().getNombre();
            String visitante = p.getEquipoVisitante().getNombre();

            jugados.put(local, jugados.getOrDefault(local, 0) + 1);
            jugados.put(visitante, jugados.getOrDefault(visitante, 0) + 1);

            if (p.getGolesLocal() > p.getGolesVisitante()) {
                puntos.put(local, puntos.getOrDefault(local, 0) + 3);
            } else if (p.getGolesLocal() < p.getGolesVisitante()) {
                puntos.put(visitante, puntos.getOrDefault(visitante, 0) + 3);
            } else {
                puntos.put(local, puntos.getOrDefault(local, 0) + 1);
                puntos.put(visitante, puntos.getOrDefault(visitante, 0) + 1);
            }
        }

        List<String[]> tabla = new ArrayList<>();
        for (Equipo e : equipos) {
            String nombreEquipo = e.getNombre();
            tabla.add(new String[] {
                    nombreEquipo,
                    String.valueOf(jugados.get(nombreEquipo)),
                    String.valueOf(puntos.get(nombreEquipo))
            });
        }

        // Ordena de mayor a menor puntaje (burbuja simple, sin depender de lambdas)
        for (int i = 0; i < tabla.size() - 1; i++) {
            for (int j = 0; j < tabla.size() - 1 - i; j++) {
                int puntosActual = Integer.parseInt(tabla.get(j)[2]);
                int puntosSiguiente = Integer.parseInt(tabla.get(j + 1)[2]);
                if (puntosActual < puntosSiguiente) {
                    String[] temp = tabla.get(j);
                    tabla.set(j, tabla.get(j + 1));
                    tabla.set(j + 1, temp);
                }
            }
        }

        return tabla;
    }

    /** Genera el reporte final del torneo usando StringBuilder. */
    public String generarReporteFinal() {
        StringBuilder sb = new StringBuilder();

        sb.append("=========================================\n");
        sb.append("REPORTE FINAL DEL TORNEO: ").append(nombre).append("\n");
        sb.append("=========================================\n\n");

        sb.append("EQUIPOS REGISTRADOS (").append(equipos.size()).append(")\n");
        for (Equipo e : equipos) {
            sb.append("- ").append(e.getNombre());
            if (e.getEntrenador() != null) {
                sb.append(" | Entrenador: ").append(e.getEntrenador().getNombre());
            }
            sb.append(" | Jugadores: ").append(e.getJugadores().size()).append("\n");
        }

        sb.append("\nPARTIDOS (").append(partidos.size()).append(")\n");
        for (Partido p : partidos) {
            sb.append("- ").append(p.toString()).append("\n");
        }

        sb.append("\nTABLA DE POSICIONES\n");
        sb.append(String.format("%-20s %-5s %-5s%n", "Equipo", "PJ", "Pts"));
        for (String[] fila : calcularTablaPosiciones()) {
            sb.append(String.format("%-20s %-5s %-5s%n", fila[0], fila[1], fila[2]));
        }

        return sb.toString();
    }
}
