
import java.util.Random;
import java.util.Scanner;

public class Tablero {

    private static final int TAMANO_TABLERO = 5;
    private String[][] tableroJugador;
    private String[][] tableroBot;

    public void iniciarJuego() {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Bienvenido a mi programa: Battleship");
        entrada.nextLine();

        int modoJuego = elegirModoJuego(entrada);

        if (modoJuego == 1) {
            // Modo de un jugador
            int dificultadBot = elegirDificultadBot(entrada);
            jugarUnJugador(dificultadBot);
        } else if (modoJuego == 2) {
            // Modo de dos jugadores
            tableroJugador = new String[TAMANO_TABLERO][TAMANO_TABLERO];
            tableroBot = new String[TAMANO_TABLERO][TAMANO_TABLERO];

            // Inicializar tableros para ambos jugadores
            for (int i = 0; i < TAMANO_TABLERO; i++) {
                for (int j = 0; j < TAMANO_TABLERO; j++) {
                    tableroJugador[i][j] = "_";
                    tableroBot[i][j] = "_";
                }
            }

            // Colocar barcos para ambos jugadores
            colocarBarcos(tableroJugador, 1, entrada);
            colocarBarcos(tableroBot, 2, null);  // No se necesita entrada del bot para colocar barcos

            // Juego principal
            int partesDestruidasJugador = 0;
            int partesDestruidasBot = 0;

            while (true) {
                // Llamar al método realizarAtaque con el tablero del bot atacado
                partesDestruidasBot += realizarAtaque(tableroJugador, tableroBot, 1);

                // Verificar condición de finalización del juego para el jugador
                if (partesDestruidasBot >= 3) {
                    System.out.println("¡Has ganado!");
                    break;
                }

                // Llamar al método printBoard con el tablero del jugador después del ataque (modo silencioso)
                System.out.println("Tablero actualizado del Jugador:");
                printBoard(tableroJugador, true);

                // Llamar al método realizarAtaque con el tablero del jugador atacado
                partesDestruidasJugador += realizarAtaque(tableroBot, tableroJugador, 2);

                // Verificar condición de finalización del juego para el bot
                if (partesDestruidasJugador >= 3) {
                    System.out.println("¡Has perdido! El bot ha ganado.");
                    break;
                }

                // Llamar al método printBoard con el tablero del bot después del ataque (modo silencioso)
                System.out.println("Tablero actualizado del Bot:");
                printBoard(tableroBot, true);
            }
        }
    }

    private int elegirModoJuego(Scanner entrada) {
        int modoJuego = 0;
        while (modoJuego != 1 && modoJuego != 2) {
            System.out.println("Elige uno de los dos números 1 o 2");
            System.out.println("1.) Modo de un jugador");
            System.out.println("2.) Modo de dos jugadores");
            modoJuego = entrada.nextInt();

            if (modoJuego != 1 && modoJuego != 2) {
                System.out.println("Por favor, elige una opción válida.");
            }
        }
        return modoJuego;
    }

    private int elegirDificultadBot(Scanner entrada) {
        int dificultad = 0;
        while (dificultad < 1 || dificultad > 3) {
            System.out.println("Elige la dificultad del Bot (1- Fácil, 2- Media, 3- Difícil):");
            dificultad = entrada.nextInt();

            if (dificultad < 1 || dificultad > 3) {
                System.out.println("Por favor, elige una dificultad válida.");
            }
        }
        return dificultad;
    }

    private void colocarBarcos(String[][] tablero, int jugador, Scanner entrada) {
        System.out.println("Jugador " + jugador + ", coloca tus barcos.");
        for (int i = 0; i < 3; i++) {
            int fila, columna;
            do {
                System.out.println("Posición del barco " + (i + 1) + ":");
                System.out.print("Coordenadas (fila,columna): ");
                entrada.nextLine();  // Consumir la nueva línea pendiente
                String coordenadas = entrada.nextLine();
                String[] partes = coordenadas.split(",");
                if (partes.length != 2) {
                    System.out.println("Formato de coordenadas incorrecto. Inténtalo de nuevo.");
                    continue;
                }
                try {
                    fila = Integer.parseInt(partes[0]) - 1;
                    columna = Integer.parseInt(partes[1]) - 1;

                    if (fila < 0 || fila >= TAMANO_TABLERO || columna < 0 || columna >= TAMANO_TABLERO) {
                        System.out.println("Ups, parece que has ingresado un valor incorrecto. Inténtalo de nuevo.");
                        continue;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Coordenadas inválidas. Inténtalo de nuevo.");
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

    private int realizarAtaque(String[][] tableroAtacante, String[][] tableroAtacado, int jugador) {
        int partesDestruidas = 0;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Turno del Jugador " + jugador);
        int fila, columna;
        do {
            System.out.print("Coordenada para atacar (fila,columna): ");
            String coordenadas = entrada.nextLine();
            String[] partes = coordenadas.split(",");
            if (partes.length != 2) {
                System.out.println("Formato de coordenadas incorrecto. Inténtalo de nuevo.");
                continue;
            }
            try {
                fila = Integer.parseInt(partes[0]) - 1;
                columna = Integer.parseInt(partes[1]) - 1;

                if (fila < 0 || fila >= TAMANO_TABLERO || columna < 0 || columna >= TAMANO_TABLERO) {
                    System.out.println("Ups, parece que has ingresado un valor incorrecto. Inténtalo de nuevo.");
                    continue;
                }

            } catch (NumberFormatException e) {
                System.out.println("Coordenadas inválidas. Inténtalo de nuevo.");
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
                tableroAtacado[fila][columna] = "-";
            }
            break;  // Sale del bucle si las coordenadas son válidas
        } while (true);

        return partesDestruidas;
    }

    private void printBoard(String[][] board, boolean modoSilencioso) {
        System.out.print("  ");
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        for (int i = 0; i < TAMANO_TABLERO; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                if (modoSilencioso && board[i][j].equals("B")) {
                    System.out.print("_ ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private void jugarUnJugador(int dificultadBot) {
        // Implementación del juego contra el bot
        Random rand = new Random();
        tableroJugador = new String[TAMANO_TABLERO][TAMANO_TABLERO];
        tableroBot = new String[TAMANO_TABLERO][TAMANO_TABLERO];

        // Inicializar tableros para ambos jugadores
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                tableroJugador[i][j] = "_";
                tableroBot[i][j] = "_";
            }
        }

        // Colocar barcos para el jugador
        colocarBarcos(tableroJugador, 1, null);  // No se necesita entrada del jugador para colocar barcos

        // Colocar barcos para el bot (dependiendo de la dificultad)
        colocarBarcosBot(tableroBot, dificultadBot);

        // Juego principal
        int partesDestruidasJugador = 0;
        int partesDestruidasBot = 0;

        while (true) {
            // Llamar al método realizarAtaque con el tablero del bot atacado
            partesDestruidasBot += realizarAtaque(tableroJugador, tableroBot, 1);

            // Verificar condición de finalización del juego para el jugador
            if (partesDestruidasBot >= 3) {
                System.out.println("¡Has ganado contra el Bot!");
                break;
            }

            // Llamar al método printBoard con el tablero del jugador después del ataque (modo silencioso)
            System.out.println("Tablero actualizado del Jugador:");
            printBoard(tableroJugador, true);

            // Llamar al método realizarAtaque con el tablero del jugador atacado
            partesDestruidasJugador += realizarAtaqueBot(tableroBot, tableroJugador, dificultadBot);

            // Verificar condición de finalización del juego para el bot
            if (partesDestruidasJugador >= 3) {
                System.out.println("¡Has perdido! El Bot ha ganado.");
                break;
            }

            // Llamar al método printBoard con el tablero del bot después del ataque (modo silencioso)
            System.out.println("Tablero actualizado del Bot:");
            printBoard(tableroBot, true);
        }
    }

    private void colocarBarcosBot(String[][] tablero, int dificultad) {
        Random rand = new Random();
        int numBarcos = 3;

        // Colocar barcos según la dificultad
        while (numBarcos > 0) {
            int fila = rand.nextInt(TAMANO_TABLERO);
            int columna = rand.nextInt(TAMANO_TABLERO);

            if (tablero[fila][columna].equals("_")) {
                tablero[fila][columna] = "B";
                numBarcos--;
            }
        }
    }

    private int realizarAtaqueBot(String[][] tableroAtacante, String[][] tableroAtacado, int dificultad) {
        int partesDestruidas = 0;
        Random rand = new Random();

        System.out.println("Turno del Bot");
        int fila, columna;

        // Simular dificultad del bot (fácil, media o difícil)
        switch (dificultad) {
            case 1:  // Fácil: Atacar aleatoriamente sin repetir posiciones
                do {
                    fila = rand.nextInt(TAMANO_TABLERO);
                    columna = rand.nextInt(TAMANO_TABLERO);
                } while (!tableroAtacante[fila][columna].equals("-") && !tableroAtacante[fila][                    columna].equals("-") && !tableroAtacante[fila][columna].equals("_"));
                break;
            case 2:  // Media: Atacar aleatoriamente
                fila = rand.nextInt(TAMANO_TABLERO);
                columna = rand.nextInt(TAMANO_TABLERO);
                break;
            case 3:  // Difícil: Atacar posiciones adyacentes a barcos destruidos
                // Implementar lógica para atacar posiciones adyacentes (opcional)
                fila = rand.nextInt(TAMANO_TABLERO);
                columna = rand.nextInt(TAMANO_TABLERO);
                break;
            default:
                fila = rand.nextInt(TAMANO_TABLERO);
                columna = rand.nextInt(TAMANO_TABLERO);
                break;
        }

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
            tableroAtacado[fila][columna] = "-";
        }

        return partesDestruidas;
    }

    public static void main(String[] args) {
        Tablero juego = new Tablero();
        juego.iniciarJuego();
    }
}
