package com.torneo.modelo;

import java.time.LocalDate;

/**
 * Extension opcional: interfaz con un metodo abstracto, uno default
 * y uno static. La implementa Equipo.
 */
public interface Notificable {

    void notificar(String mensaje);

    default void notificarConFecha(String mensaje) {
        System.out.println("[" + LocalDate.now() + "] " + mensaje);
    }

    static String formatoEstandar(String mensaje) {
        return ">> " + mensaje;
    }
}
