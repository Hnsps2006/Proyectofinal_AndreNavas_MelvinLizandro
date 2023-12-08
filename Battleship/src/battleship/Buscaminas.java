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
public class Buscaminas {
    private char[][] minas;
    private Scanner entrada;

    public Buscaminas() {
        minas = new char[][]{
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
        };
        entrada = new Scanner(System.in);
    }

    public void jugar() {
        System.out.println("Bienvenido");

        Random aleatorio = new Random();
        int opcionAleatoria = aleatorio.nextInt(2);

        if (opcionAleatoria == 0) {
            jugarPartida(0);
        } else {
            jugarPartida(1);
        }
    }

    private void jugarPartida(int opcion) {
        int contador = 0;

        if (opcion == 0) {
            inicializarMinasOpcion0();
        } else {
            inicializarMinasOpcion1();
        }

        while (juegoEnCurso(opcion)) {
            imprimirTablero();
            System.out.println("  0  1  2  3  4");
            System.out.print("Ingrese la posición a atacar, ejemplo(4:1): ");
            String j1 = entrada.nextLine();

            int columna = j1.charAt(0) - '0';
            if (columna >= 0 && columna <= 4) {
                String fila = j1.substring(2);
                int fila2 = Integer.parseInt(fila) - 1;

                if (fila2 >= 0 && fila2 <= 5 && minas[columna][fila2] == ' ') {
                    minas[columna][fila2] = 'X';
                } else {
                    System.out.println("La posición ingresada no es válida o ya ha sido atacada. Inténtalo de nuevo.");
                }
            } else {
                System.out.println("La columna ingresada no es válida. Inténtalo de nuevo.");
            }

            contador++;
            if (contador == 22) {
                break;
            }
        }

        if (juegoGanado(opcion)) {
            System.out.println("**Felicidades has ganado**");
        } else {
            System.out.println("¡BOOOOMMM!");
            System.out.println("Tristemente has perdido");
        }
    }

    private void inicializarMinasOpcion0() {
        minas[0][0] = '*';
        minas[1][0] = '*';
        minas[2][0] = '*';
        minas[1][2] = '*';
        minas[1][3] = '*';
        minas[3][4] = '*';
        minas[4][4] = '*';
        minas[5][4] = '*';
    }

    private void inicializarMinasOpcion1() {
        minas[1][3] = '*';
        minas[2][3] = '*';
        minas[3][3] = '*';
        minas[3][1] = '*';
        minas[4][1] = '*';
        minas[5][1] = '*';
        minas[5][3] = '*';
        minas[5][4] = '*';
    }

    private boolean juegoEnCurso(int opcion) {
        if (opcion == 0) {
            return minas[0][0] == ' ' && minas[1][0] == ' ' && minas[2][0] == ' ' && minas[1][2] == ' '
                    && minas[1][3] == ' ' && minas[3][4] == ' ' && minas[4][4] == ' ' && minas[5][4] == ' ';
        } else {
            return minas[1][3] == ' ' && minas[2][3] == ' ' && minas[3][3] == ' ' && minas[3][1] == ' '
                    && minas[4][1] == ' ' && minas[5][1] == ' ' && minas[5][3] == ' ' && minas[5][4] == ' ';
        }
    }

    private boolean juegoGanado(int opcion) {
        if (opcion == 0) {
            return minas[0][0] == ' ' && minas[1][0] == ' ' && minas[2][0] == ' ' && minas[1][2] == ' '
                    && minas[1][3] == ' ' && minas[3][4] == ' ' && minas[4][4] == ' ' && minas[5][4] == ' ';
        } else {
            return minas[1][3] == ' ' && minas[2][3] == ' ' && minas[3][3] == ' ' && minas[3][1] == ' '
                    && minas[4][1] == ' ' && minas[5][1] == ' ' && minas[5][3] == ' ' && minas[5][4] == ' ';
        }
    }

    private void imprimirTablero() {
        for (int i = 0; i < minas.length; i++) {
            System.out.print(i);
            for (int j = 0; j < minas[0].length; j++) {
                System.out.print("[" + minas[i][j] + "]");
            }
            System.out.println("");
        }
    }
}