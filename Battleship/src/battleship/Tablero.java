/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author eliza
 */
class Tablero { 

private static final int TAMANO_TABLERO = 5;
    public static int numFilas = 6;
    public static int numColumnas = 5;
    public static int barcosJugador;
    public static int barcosComputadora;
    public static String[][] tablero = new String[numFilas][numColumnas];
    public static int[][] intentosFallidos = new int[numFilas][numColumnas];
    public static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("**** Bienvenido al juego de Batalla Naval ****");
        System.out.println("En este momento, el mar está vacío\n");

        // Paso 1: Crear el mapa oceánico
        crearMapaOceanico();

        // Paso 2: Desplegar barcos del jugador
        desplegarBarcosJugador();

        // Paso 3: Desplegar barcos de la computadora
        desplegarBarcosComputadora();

        // Paso 4: Batalla
        do {
            batalla();
        } while (barcosJugador != 0 && barcosComputadora != 0);

        // Paso 5: Fin del juego
        finDelJuego();
    }

    public static void crearMapaOceanico() {
        // Primera sección del mapa oceánico
        System.out.print("  ");
        for (int i = 0; i < numColumnas; i++)
            System.out.print(i);
        System.out.println();

        // Sección media del mapa oceánico
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "|" + tablero[i][j]);
                else if (j == tablero[i].length - 1)
                    System.out.print(tablero[i][j] + "|" + i);
                else
                    System.out.print(tablero[i][j]);
            }
            System.out.println();
        }

        // Última sección del mapa oceánico
        System.out.print("  ");
        for (int i = 0; i < numColumnas; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void desplegarBarcosJugador() {
        Scanner input = new Scanner(System.in);

        System.out.println("\nDespliega tus barcos:");
        // Desplegando cinco barcos para el jugador
        barcosJugador = 3;
        for (int i = 1; i <= barcosJugador; ) {
            System.out.print("Ingresa la coordenada X para tu " + i + " barco: ");
            int x = input.nextInt();
            System.out.print("Ingresa la coordenada Y para tu " + i + " barco: ");
            int y = input.nextInt();

            if ((x >= 0 && x < numFilas) && (y >= 0 && y < numColumnas) && (tablero[x][y].equals(" "))) {
                tablero[x][y] = "@";
                i++;
            } else if ((x >= 0 && x < numFilas) && (y >= 0 && y < numColumnas) && tablero[x][y].equals("@"))
                System.out.println("No puedes colocar dos o más barcos en la misma ubicación");
            else if ((x < 0 || x >= numFilas) || (y < 0 || y >= numColumnas))
                System.out.println("No puedes colocar barcos fuera de la cuadrícula " + numFilas + " por " + numColumnas);
        }
        imprimirMapaOceanico();
    }

    public static void desplegarBarcosComputadora() {
        System.out.println("\nLa computadora está desplegando barcos");
        // Desplegando cinco barcos para la computadora
        barcosComputadora = 3;
        for (int i = 1; i <= barcosComputadora; ) {
            int x = random.nextInt(numFilas);
            int y = random.nextInt(numColumnas);

            if ((x >= 0 && x < numFilas) && (y >= 0 && y < numColumnas) && (tablero[x][y].equals(" "))) {
                tablero[x][y] = "x";
                System.out.println(i + ". barco DESPLEGADO");
                i++;
            }
        }
        imprimirMapaOceanico();
    }

    public static void batalla() {
        turnoJugador();
        turnoComputadora();

        imprimirMapaOceanico();

        System.out.println();
        System.out.println("Tus barcos: " + barcosJugador + " | Barcos de la computadora: " + barcosComputadora);
        System.out.println();
    }

    public static void turnoJugador() {
        System.out.println("\nTU TURNO");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Ingresa la coordenada X: ");
            x = input.nextInt();
            System.out.print("Ingresa la coordenada Y: ");
            y = input.nextInt();

            if ((x >= 0 && x < numFilas) && (y >= 0 && y < numColumnas)) // conjetura válida
            {
                if (tablero[x][y].equals("x")) {
                    System.out.println("¡Boom! Hundiste el barco de la computadora.");
                    tablero[x][y] = "!";
                    --barcosComputadora;
                } else if (tablero[x][y].equals("@")) {
                    System.out.println("Oh no, hundiste tu propio barco :(");
                    tablero[x][y] = "x";
                    --barcosJugador;
                    ++barcosComputadora;
                } else if (tablero[x][y].equals(" ")) {
                    System.out.println("Lo siento, fallaste");
                    tablero[x][y] = "-";
                }
            } else if ((x < 0 || x >= numFilas) || (y < 0 || y >= numColumnas))  // conjetura inválida
                System.out.println("No puedes colocar barcos fuera de la cuadrícula " + numFilas + " por " + numColumnas);
        } while ((x < 0 || x >= numFilas) || (y < 0 || y >= numColumnas));  // seguir repitiendo hasta una conjetura válida
    }

    public static void turnoComputadora() {
        System.out.println("\nTURNO DE LA COMPUTADORA");
        // Adivina las coordenadas
        int x = -1, y = -1;
        do {
            x = random.nextInt(numFilas);
            y = random.nextInt(numColumnas);

            if ((x >= 0 && x < numFilas) && (y >= 0 && y < numColumnas)) // conjetura válida
            {
                if (tablero[x][y].equals("@")) {
                    System.out.println("La Computadora hundió uno de tus barcos.");
                    tablero[x][y] = "x";
                    --barcosJugador;
                    ++barcosComputadora;
                } else if (tablero[x][y].equals("x")) {
                    System.out.println("La Computadora hundió uno de sus propios barcos");
                    tablero[x][y] = "!";
                } else if (tablero[x][y].equals(" ")) {
                    System.out.println("La Computadora falló");
                    // Guardando conjeturas fallidas para la computadora
                    if (intentosFallidos[x][y] != 1)
                        intentosFallidos[x][y] = 1;
                }
            }
        } while ((x < 0 || x >= numFilas) || (y < 0 || y >= numColumnas));  // seguir repitiendo hasta una conjetura válida
    }

    public static void finDelJuego() {
        System.out.println("Tus barcos: " + barcosJugador + " | Barcos de la computadora: " + barcosComputadora);
        if (barcosJugador > 0 && barcosComputadora <= 0)
            System.out.println("¡Hurra! Ganaste la batalla :)");
        else
            System.out.println("Lo siento, perdiste la batalla");
        System.out.println();
    }

    public static void imprimirMapaOceanico() {
        System.out.println();
        // Primera sección del mapa oceánico
        System.out.print("  ");
        for (int i = 0; i < numColumnas; i++)
            System.out.print(i);
        System.out.println();

        // Sección media del mapa oceánico
        for (int x = 0; x < tablero.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < tablero[x].length; y++) {
                System.out.print(tablero[x][y]);
            }

            System.out.println("|" + x);
        }

        // Última sección del mapa oceánico
        System.out.print("  ");
        for (int i = 0; i < numColumnas; i++)
            System.out.print(i);
        System.out.println();
    }
}