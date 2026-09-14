package com.torneo.app;

import com.torneo.modelo.*;
import com.torneo.util.Historial;

import java.util.List;
import java.util.Scanner;

/**
 * Punto de entrada del sistema. Contiene el menu principal por consola
 * y toda la logica de interaccion con el usuario.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Torneo torneo;
    private static final Historial<String> historial = new Historial<>();

    public static void main(String[] args) {
        boolean salir = false;

        do {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    crearTorneo();
                    break;
                case 2:
                    registrarEquipo();
                    break;
                case 3:
                    registrarPersonaEnEquipo();
                    break;
                case 4:
                    programarPartido();
                    break;
                case 5:
                    registrarResultado();
                    break;
                case 6:
                    mostrarTablaPosiciones();
                    break;
                case 7:
                    buscarJugador();
                    break;
                case 8:
                    generarReporteFinalYSalir();
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo.");
            }

        } while (!salir);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE GESTION DE TORNEO =====");
        System.out.println("1. Crear torneo");
        System.out.println("2. Registrar equipo");
        System.out.println("3. Registrar jugador o entrenador en un equipo");
        System.out.println("4. Programar partido");
        System.out.println("5. Registrar resultado de partido");
        System.out.println("6. Mostrar tabla de posiciones");
        System.out.println("7. Buscar jugador por nombre");
        System.out.println("8. Generar reporte final y salir");
        System.out.println("=========================================");
    }

    // ---------------- Opcion 1: crear torneo ----------------
    private static void crearTorneo() {
        System.out.print("Nombre del torneo: ");
        String nombre = scanner.nextLine();
        torneo = new Torneo(nombre);
        historial.agregarEvento("Torneo creado: " + nombre);
        System.out.println("Torneo \"" + nombre + "\" creado con exito.");
    }

    // ---------------- Opcion 2: registrar equipo ----------------
    private static void registrarEquipo() {
        if (!hayTorneo()) return;

        System.out.print("Nombre del equipo: ");
        String nombre = scanner.nextLine();

        if (!Equipo.esNombreValido(nombre)) {
            System.out.println("Error: el nombre del equipo no puede estar vacio.");
            return;
        }

        if (torneo.buscarEquipoPorNombre(nombre) != null) {
            System.out.println("Error: ya existe un equipo con ese nombre.");
            return;
        }

        Equipo equipo = new Equipo(nombre);
        torneo.registrarEquipo(equipo);
        historial.agregarEvento("Equipo registrado: " + nombre);
        System.out.println("Equipo \"" + nombre + "\" registrado con exito.");
    }

    // ---------------- Opcion 3: registrar jugador o entrenador ----------------
    private static void registrarPersonaEnEquipo() {
        if (!hayTorneo()) return;

        if (torneo.getEquipos().isEmpty()) {
            System.out.println("Primero debe registrar al menos un equipo (opcion 2).");
            return;
        }

        System.out.print("Nombre del equipo: ");
        String nombreEquipo = scanner.nextLine();
        Equipo equipo = torneo.buscarEquipoPorNombre(nombreEquipo);

        if (equipo == null) {
            System.out.println("Equipo no encontrado.");
            return;
        }

        System.out.println("1. Jugador   2. Entrenador");
        int tipo = leerEntero("Seleccione tipo de persona: ");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Documento: ");
        String documento = scanner.nextLine();
        int edad = leerEntero("Edad: ");

        if (tipo == 1) {
            System.out.print("Posicion (ej: Delantero, Portero): ");
            String nombrePosicion = scanner.nextLine();
            Posicion posicion = new Posicion(nombrePosicion);

            int numeroCamiseta = leerEntero("Numero de camiseta: ");
            double calificacion = leerDouble("Calificacion inicial (0.0 a 10.0): ");

            Jugador jugador = new Jugador(nombre, documento, edad, posicion, numeroCamiseta);
            jugador.setCalificacion(calificacion);

            equipo.registrar(jugador); // sobrecarga de registrar()
            historial.agregarEvento("Jugador registrado: " + nombre + " en " + equipo.getNombre());
            System.out.println("Jugador registrado con exito.");

        } else if (tipo == 2) {
            int experiencia = leerEntero("Anios de experiencia: ");
            Entrenador entrenador = new Entrenador(nombre, documento, edad, experiencia);

            equipo.registrar(entrenador); // sobrecarga de registrar()
            historial.agregarEvento("Entrenador registrado: " + nombre + " en " + equipo.getNombre());
            System.out.println("Entrenador registrado con exito.");

        } else {
            System.out.println("Tipo invalido. No se registro nada.");
        }
    }

    // ---------------- Opcion 4: programar partido ----------------
    private static void programarPartido() {
        if (!hayTorneo()) return;

        if (torneo.getEquipos().size() < 2) {
            System.out.println("Se necesitan al menos 2 equipos registrados para programar un partido.");
            return;
        }

        System.out.print("Equipo local: ");
        String nombreLocal = scanner.nextLine();
        System.out.print("Equipo visitante: ");
        String nombreVisitante = scanner.nextLine();

        Equipo local = torneo.buscarEquipoPorNombre(nombreLocal);
        Equipo visitante = torneo.buscarEquipoPorNombre(nombreVisitante);

        if (local == null || visitante == null) {
            System.out.println("Error: uno o ambos equipos no existen.");
            return;
        }

        if (local == visitante) {
            System.out.println("Error: un equipo no puede jugar contra si mismo.");
            return;
        }

        System.out.print("Fecha del partido (dd/mm/aaaa): ");
        String fecha = scanner.nextLine();

        Partido partido = new Partido(local, visitante, fecha);
        torneo.programarPartido(partido);
        historial.agregarEvento("Partido programado: " + partido);
        System.out.println("Partido programado con exito.");
    }

    // ---------------- Opcion 5: registrar resultado ----------------
    private static void registrarResultado() {
        if (!hayTorneo()) return;

        if (torneo.getPartidos().isEmpty()) {
            System.out.println("No hay partidos programados todavia.");
            return;
        }

        System.out.println("Partidos disponibles:");
        int contador = 1;
        for (Partido p : torneo.getPartidos()) {
            System.out.println(contador + ". " + p);
            contador++;
        }

        int indice = leerEntero("Seleccione el numero de partido: ");

        if (indice < 1 || indice > torneo.getPartidos().size()) {
            System.out.println("Numero de partido invalido.");
            return;
        }

        Partido partido = torneo.getPartidos().get(indice - 1);

        int golesLocal = leerEntero("Goles del equipo local: ");
        int golesVisitante = leerEntero("Goles del equipo visitante: ");

        boolean registrado = partido.registrarResultado(golesLocal, golesVisitante);

        if (!registrado) {
            System.out.println("Error: los goles no pueden ser negativos. Resultado no registrado.");
            return;
        }

        historial.agregarEvento("Resultado registrado: " + partido);
        System.out.println("Resultado registrado con exito.");
    }

    // ---------------- Opcion 6: tabla de posiciones ----------------
    private static void mostrarTablaPosiciones() {
        if (!hayTorneo()) return;

        List<String[]> tabla = torneo.calcularTablaPosiciones();

        if (tabla.isEmpty()) {
            System.out.println("No hay equipos registrados todavia.");
            return;
        }

        System.out.println();
        System.out.printf("%-20s %-5s %-5s%n", "Equipo", "PJ", "Pts");
        for (String[] fila : tabla) {
            System.out.printf("%-20s %-5s %-5s%n", fila[0], fila[1], fila[2]);
        }
    }

    // ---------------- Opcion 7: buscar jugador ----------------
    private static void buscarJugador() {
        if (!hayTorneo()) return;

        System.out.print("Nombre del jugador a buscar: ");
        String nombreBuscado = scanner.nextLine();

        boolean encontrado = false;

        busqueda:
        for (Equipo equipo : torneo.getEquipos()) {
            if (equipo.getJugadores().isEmpty()) {
                continue busqueda; // continue: se omiten equipos sin jugadores
            }

            for (Jugador jugador : equipo.getJugadores()) {
                if (jugador.getNombre().equalsIgnoreCase(nombreBuscado)) {
                    System.out.println("Jugador encontrado en el equipo \"" + equipo.getNombre() + "\":");
                    System.out.println(jugador);
                    encontrado = true;
                    break busqueda; // break etiquetado: termina la busqueda por completo
                }
            }
        }

        if (!encontrado) {
            System.out.println("No se encontro ningun jugador con ese nombre.");
        }
    }

    // ---------------- Opcion 8: reporte final y salir ----------------
    private static void generarReporteFinalYSalir() {
        if (!hayTorneo()) {
            System.out.println("Saliendo del sistema...");
            return;
        }

        String reporte = torneo.generarReporteFinal();
        System.out.println("\n" + reporte);

        System.out.println("Historial de eventos del sistema:");
        historial.mostrarEventos();

        System.out.println("\nGracias por usar el Sistema de Gestion de Torneo. Hasta pronto.");
    }

    // ---------------- Utilidades ----------------

    private static boolean hayTorneo() {
        if (torneo == null) {
            System.out.println("Primero debe crear un torneo (opcion 1).");
            return false;
        }
        return true;
    }

    /** Lee un entero validando la entrada; reintenta si el texto no es numerico. */
    private static int leerEntero(String mensaje) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                valor = Integer.parseInt(entrada.trim());
                break; // entrada valida: se sale del bucle
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero.");
                continue; // entrada invalida: se repite el bucle
            }
        }
        return valor;
    }

    /** Lee un double validando la entrada; reintenta si el texto no es numerico. */
    private static double leerDouble(String mensaje) {
        double valor;
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                valor = Double.parseDouble(entrada.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero decimal (ej: 7.5).");
                continue;
            }
        }
        return valor;
    }
}
