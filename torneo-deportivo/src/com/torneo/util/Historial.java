package com.torneo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension opcional: clase generica para registrar eventos importantes
 * del torneo (creaciones, registros, resultados, etc.).
 */
public class Historial<T> {

    private List<T> eventos;

    public Historial() {
        this.eventos = new ArrayList<>();
    }

    public void agregarEvento(T evento) {
        eventos.add(evento);
    }

    public List<T> getEventos() {
        return eventos;
    }

    public void mostrarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("(sin eventos registrados)");
            return;
        }
        for (T evento : eventos) {
            System.out.println("- " + evento);
        }
    }
}
