package BuscaMinas;


public class Casilla {
    boolean bomba = false;
    boolean  tapada = true;
    boolean marcada = false;
    int bombasCerca = 0;
    int fila;
    int columna;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Casilla (int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }


    public void desMarcar() {
        marcada = false;
    }

    public String getCasilla (){

        if (tapada==false){
            if(bomba){
                return ANSI_RED + "[ X ] " + ANSI_RESET;
            }
            if(bombasCerca>0) {
                return ANSI_YELLOW + ". " + bombasCerca + " . " + ANSI_RESET;
            }else{

                return ANSI_GREEN + "[   ] " + ANSI_RESET;
            }
        } else {
            if (marcada) {
                return ANSI_BLUE + "[ M ] " + ANSI_RESET;
            }
        }
        return "[" + columna + "," + fila + "] ";
    }

    public boolean isBomba(){
        return bomba;
    }

    public boolean IsTapada(){
        return tapada;
    }



    public boolean destapar(){

        if (!tapada) {
            return false;
        }


        tapada = false;
        Tablero.contadorCasillasDestapadas++;

        for (int i = fila-1; i <= fila+1; i++) {
            for (int k = columna - 1; k <= columna + 1; k++) {

                if (i >= 0 && i <= Tablero.tablero.length - 1 && k >= 0 && k <= Tablero.tablero.length - 1) {

                    if (Tablero.tablero[fila][columna].bombasCerca == 0) {
                        Tablero.tablero[i][k].destapar();
                    }
                }
            }
        }

        return tapada;
    }
    public boolean marcar(){
        marcada = true;
        return marcada;
    }
    public void anyadirBombaCerca(){
        bombasCerca=bombasCerca+1;
    }

    public void setBomba(){
        bomba = true;
    }

}
