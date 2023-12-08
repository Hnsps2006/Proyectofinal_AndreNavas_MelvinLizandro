/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author eliza
 */
public class solojugador {

    private static final int TAMANO_TABLERO = 5;
    private String[][] tableroJugador;
    private String[][] tableroBot;

    public solojugador(String[][] tableroJugador, String[][] tableroBot) {
        this.tableroJugador = tableroJugador;
        this.tableroBot = tableroBot;
    }

    void inicializarTablero(String[][] tablero) {
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                tablero[i][j] = "_";
            }
        }
    }

    void colocarBarcos(String[][] tablero, int jugador) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Jugador " + jugador + ", coloca tus barcos.");

        for (int i = 0; i < 3; i++) {
            int fila, columna;

            do {
                System.out.println("Posición del barco " + (i + 1) + ":");
                System.out.print("Coordenadas (fila,columna): ");
                String coordenadas = entrada.nextLine();
                String[] partes = coordenadas.split(",");

                if (partes.length != 2 || !esEntero(partes[0]) || !esEntero(partes[1])) {
                    System.out.println("Formato de coordenadas incorrecto. Inténtalo de nuevo.");
                    continue;
                }

                fila = Integer.parseInt(partes[0]) - 1;
                columna = Integer.parseInt(partes[1]) - 1;

                if (fila < 0 || fila >= TAMANO_TABLERO || columna < 0 || columna >= TAMANO_TABLERO) {
                    System.out.println("Ups, parece que has ingresado un valor incorrecto. Inténtalo de nuevo.");
                    continue;
                }

                if (!tablero[fila][columna].equals("_")) {
                    System.out.println("Ya hay un barco en esa posición. Inténtalo de nuevo.");
                } else {
                    tablero[fila][columna] = "B";
                    break;
                }
            } while (true);
        }
    }

    void colocarBarcosBot(String[][] tablero) {
        Random rand = new Random();
        int numBarcos = 3;

        while (numBarcos > 0) {
            int fila = rand.nextInt(TAMANO_TABLERO);
            int columna = rand.nextInt(TAMANO_TABLERO);

            if (tablero[fila][columna].equals("_")) {
                tablero[fila][columna] = "B";
                numBarcos--;
            }
        }
    }

    private boolean esEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    int realizarAtaqueBot(String[][] tableroAtacante, String[][] tableroAtacado) {
        int partesDestruidas = 0;
        Random rand = new Random();

        System.out.println("Turno del Bot");
        int fila, columna;

        do {
            fila = rand.nextInt(TAMANO_TABLERO);
            columna = rand.nextInt(TAMANO_TABLERO);
        } while (!tableroAtacante[fila][columna].equals("-") && !tableroAtacante[fila][columna].equals("_"));

        if (tableroAtacado[fila][columna].equals("B")) {
            System.out.println("¡Barco destruido por el Bot!");
            partesDestruidas++;
            tableroAtacante[fila][columna] = "X";
            tableroAtacado[fila][columna] = "X";
        } else if (tableroAtacado[fila][columna].equals("X")) {
            System.out.println("El Bot ya ha atacado esa posición. Turno del Jugador.");
        } else {
            System.out.println("El Bot ha fallado el ataque. Turno del Jugador.");
            tableroAtacante[fila][columna] = "-";
            tableroAtacado[fila][columna] = "X";  // Marcar como atacado
        }

        return partesDestruidas;
    }

    void imprimirTablero(String[][] tablero, String titulo) {
        System.out.println(titulo);
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    int realizarAtaque(String[][] tableroAtacante, String[][] tableroAtacado, int jugador) {
        int partesDestruidas = 0;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Turno del Jugador " + jugador);
        int fila, columna;

        do {
            System.out.print("Coordenada para atacar (fila,columna): ");
            String[] coordenadas = entrada.nextLine().split(",");

            if (coordenadas.length != 2 || !esEntero(coordenadas[0]) || !esEntero(coordenadas[1])) {
                System.out.println("Formato de coordenadas incorrecto. Inténtalo de nuevo.");
                continue;
            }

            fila = Integer.parseInt(coordenadas[0]) - 1;
            columna = Integer.parseInt(coordenadas[1]) - 1;

            if (fila < 0 || fila >= TAMANO_TABLERO || columna < 0 || columna >= TAMANO_TABLERO) {
                System.out.println("Coordenadas fuera de rango. Inténtalo de nuevo.");
                continue;
            }

            if (tableroAtacado[fila][columna].equals("B")) {
                System.out.println("¡Barco destruido!");
                partesDestruidas++;
                tableroAtacante[fila][columna] = "X";
                tableroAtacado[fila][columna] = "X";
            } else if (tableroAtacado[fila][columna].equals("X")) {
                System.out.println("Ya has atacado esa posición. Inténtalo de nuevo.");
            } else {
                System.out.println("Agua. Sigue intentándolo.");
                tableroAtacante[fila][columna] = "-";
                tableroAtacado[fila][columna] = "X";  // Marcar como atacado
            }
            break;
        } while (true);

        return partesDestruidas;
    } 
    
    boolean hayGanador(String[][] tableroJugador, String[][] tableroBot) {
        if (todosBarcosHundidos(tableroJugador)) {
            System.out.println("¡El Bot ganó! Todos tus barcos fueron destruidos.");
            return true;
        } else if (todosBarcosHundidos(tableroBot)) {
            System.out.println("¡Jugador ganó! Todos los barcos del Bot fueron destruidos.");
            return true;
        }
        return false;
    }

    private boolean todosBarcosHundidos(String[][] tablero) {
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                if (tablero[i][j].equals("B")) {
                    return false;
                }
            }
        }
        return true;
    }
}

