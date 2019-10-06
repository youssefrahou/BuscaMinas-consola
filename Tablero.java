package BuscaMinas;

import java.util.Scanner;


public class Tablero {

    static int columna;
    static int fila;

    int totalCasillas;
    private int minas;
    private int dificultad;
    private boolean finPartida = false;
    static Casilla[][] tablero;
    int contadorCasillasMarcadas = 0;
    static int contadorCasillasDestapadas;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";


    public void configurar() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Elija la dificultad:");
        System.out.println("1: Fácil" + "\n" + "2: Medio" + "\n" + "3: Difícil" + "\n" + "4: Muy difícil");
        dificultad = entrada.nextInt();

        switch (dificultad) {

            case 1:
                columna = 5;
                fila = 5;

                float fac = ((0.05f) * (columna * fila));
                minas = (int) fac;
                System.out.println(minas);
                break;

            case 2:
                columna = 7;
                fila = 7;

                float med = ((0.15f) * (columna * fila));
                minas = (int) med;
                System.out.println(minas);
                break;
            case 3:
                columna = 8;
                fila = 8;

                float dif = ((0.30f) * (columna * fila));
                minas = (int) dif;
                System.out.println(minas);
                break;
            case 4:
                columna = 9;
                fila = 9;

                float mdif = ((0.50f) * (columna * fila));
                minas = (int) mdif;
                System.out.println(minas);
                break;
            default:

                for (int i = 0; i < 20; i++) {
                    System.out.println();
                }
                System.out.println(ANSI_RED + "Escriba un número correcto" + ANSI_RESET);
                System.out.println();
                System.out.println();
                configurar();
        }
    }

    public void crearTablero(){

        tablero = new Casilla[fila][columna];

        for (int i=0; i < tablero.length; i++){
            for(int j=0; j < tablero[0].length; j++){
                tablero[i][j] = new Casilla(i, j);
            }
        }
        genBombas();
        anyadirBombasCerca();
        totalCasillas = tablero.length * tablero[0].length;
    }
    public void genBombas() {
        int n, m;

        for (int i = 0; i < minas; i++) {
            do {
                n = (int) (Math.random() * columna);
                m = (int) (Math.random() * fila);
            }
            while (tablero[n][m].bomba);

            tablero[n][m].setBomba();
        }
    }

    public void anyadirBombasCerca() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {

                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {

                        if (k >= 0 && k <= tablero.length - 1 && l >= 0 && l <= tablero.length - 1) {
                            if (tablero[i][j].isBomba()) {
                                tablero[k][l].anyadirBombaCerca();
                            }
                        }
                    }
                }
            }
        }
    }

    public void imprimirTablero(){
        System.out.println("");
        for (int i = 0; i < tablero.length; i++) {

            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print(tablero[i][j].getCasilla()+ " ");
            }
            System.out.println();
        }
    }


    public boolean finPartida(){
        return finPartida;
    }
    public void setFinPartida(){
        finPartida=true;
    }

    public void pedirDatos(){
        Scanner entrada = new Scanner(System.in);

        do {
            System.out.println(ANSI_GREEN + "¿Qué quieres hacer?" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Pulse un número: " + "\n" + ANSI_RESET + "1: Destapar casilla" + "\n" + "2: Marcar/desmarcar casilla");
            int opcion = entrada.nextInt();

            if (opcion == 1) {
                System.out.print("Columna: ");
                int columna = entrada.nextInt();

                System.out.print("Fila: ");
                int fila = entrada.nextInt();


                if (tablero[fila][columna].IsTapada() == true && !tablero[fila][columna].marcada) {
                    tablero[fila][columna].destapar();
                }

                imprimirTablero();
                System.out.println("Casillas destapadas: " + contadorCasillasDestapadas);

                if (contadorCasillasDestapadas == (totalCasillas - minas) && !tablero[fila][columna].bomba) {
                    System.out.println(ANSI_GREEN + "¡ENHORABUENA! ¡HAS GANADO LA PARTIDA!" + ANSI_RESET);
                    setFinPartida();
                }
                if (tablero[fila][columna].bomba) {
                    System.out.println(ANSI_RED + "¡HAS PISADO UNA MINA!" + ANSI_RESET);
                    setFinPartida();
                }

            } else {
                System.out.print("Columna: ");
                int col = entrada.nextInt();

                System.out.print("Fila: ");
                int fil = entrada.nextInt();

                if (tablero[fil][col].IsTapada() == true && !tablero[fil][col].marcada) {
                    tablero[fil][col].marcar();
                    contadorCasillasMarcadas++;
                } else {
                    tablero[fil][col].desMarcar();
                }
                imprimirTablero();
            }

        } while (!finPartida());



    }


    public void jugar(){
        configurar();
        crearTablero();
        imprimirTablero();
        try {
            pedirDatos();
        } catch (ArrayIndexOutOfBoundsException exc) {
            for (int i = 0; i < 50; ++i) {
                System.out.println();
            }
            System.out.println(ANSI_RED + "Por favor, introduzca un número válido." + ANSI_RESET);
            pedirDatos();
        }
    }

}
