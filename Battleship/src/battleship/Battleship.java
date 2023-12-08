/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package battleship;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author eliza
 */
public class Battleship {  
public static void main(String[] args) { 
    Scanner entrada = new Scanner(System.in);
    System.out.print("Bienvenido a mi programa");
    entrada.nextLine();
    // esto obliga al usuario a hacer enter para que el programa pueda seguir
    System.out.println("Elija una de las siguientes ocpiones: ");
    System.out.println("1. opcion 1.Battleship");
    System.out.println("2. opcion 2.Buscaminas");
    int option = entrada.nextInt();
    // sirve para leer un entero y darle la option al usuario
    //de ingresar un numero
    
    switch (option) {
        case 1:
            System.out.println("Usted ha elegido la opción número 1.battleship"); 
            menuJuego();
            break;
        case 2:
            System.out.println("Usted ha elegido la opción número 2.buscaminas"); 
            Buscaminas buscaminas = new Buscaminas();
            buscaminas.jugar();           
            break;
        default:
            System.out.println("Vuelvalo a intentar");
    }
//* Esto es como un ciclo donde uno tiene diferentes caminos a tomar,
//que son los case, la funcion del swicth es poner la condición como si
//fuera un if, el break sirve para terminar el ciclo (en otras palabras 
//terminar con el caso y no seguir adelante.La condición se pone adentro
// de los parentesis. //*
    }

    public static void menuJuego() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("**** Menú de Juego ****");
            System.out.println("1. Un jugador");
            System.out.println("2. Dos jugadores");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Lógica para un solo jugador
                    System.out.println("**** Bienvenido al juego de Batalla Naval ****");
                    System.out.println("En este momento, el mar está vacío\n");

                    // Crear una instancia de BatallaNaval
                    Tablero batallaNaval = new Tablero();

                    // Llamar a los métodos de BatallaNaval
                    Tablero.crearMapaOceanico();
                    Tablero.desplegarBarcosJugador();
                    Tablero.desplegarBarcosComputadora();

                    // Batalla
                    do {
                        Tablero.batalla();
                    } while (Tablero.barcosJugador != 0 && Tablero.barcosComputadora != 0);

                    // Fin del juego
                    Tablero.finDelJuego();
                                break;
                case 2:
                        // Crear tableros para jugador y bot
                    String[][] tableroJugador = new String[5][5];
                    String[][] tableroBot = new String[5][5];

                    // Crear instancia de la clase SoloJugador
                    solojugador juego = new solojugador(tableroJugador, tableroBot);

                    // Inicializar tableros
                    juego.inicializarTablero(tableroJugador);
                    juego.inicializarTablero(tableroBot);

                    // Colocar barcos del jugador y de la computadora
                    juego.colocarBarcos(tableroJugador, 1);
                    juego.colocarBarcosBot(tableroBot);

                    // Realizar ataques hasta que se hundan todos los barcos de un jugador
                    int jugadorActual = 1;
                    while (true) {
                        if (jugadorActual == 1) {
                            juego.realizarAtaque(tableroJugador, tableroBot, jugadorActual);
                        } else {
                            juego.realizarAtaqueBot(tableroBot, tableroJugador);
                        }

                        // Imprimir tablero actual
                        juego.imprimirTablero(tableroJugador, "Tablero del Jugador");
                        juego.imprimirTablero(tableroBot, "Tablero del Bot");

                        // Verificar si algún jugador ganó
                        if (juego.hayGanador(tableroJugador, tableroBot)) {
                            break;
                        }

                        // Cambiar al siguiente jugador
                        jugadorActual = 3 - jugadorActual; // Alternar entre 1 y 2
                    }
                    break;
                case 0:
                    System.out.println("Gracias por jugar. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, elige nuevamente.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}