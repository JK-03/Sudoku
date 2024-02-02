/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jenniferbueso
 */
public class Tablero implements Generable{
    private int[][] tablero;
    private Random random;

    public Tablero() {
        tablero = new int[9][9];
        random = new Random();
    }
    
    public void generar() {
        llenarTablero(0, 0);
        quitarNumerosAleatorios();
    }

    public void quitarNumerosAleatorios() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (random.nextInt(3) != 0) {
                    tablero[i][j] = 0;
                }
            }
        }
    }

    public boolean llenarTablero(int fila, int columna) {
        if (fila == 9) {
            return true;
        }

        int siguienteFila = (columna == 8) ? fila + 1 : fila;
        int siguienteColumna = (columna + 1) % 9;

        int[] numeros = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(numeros); 

        for (int numero : numeros) {
            if (esMovimientoValido(fila, columna, numero)) {
                tablero[fila][columna] = numero;
                if (llenarTablero(siguienteFila, siguienteColumna)) {
                    return true;
                }
                tablero[fila][columna] = 0;
            }
        }

        return false; 
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public void establecerCelda(int fila, int columna, int valor) {
        tablero[fila][columna] = valor;
    }

    public int obtenerCelda(int fila, int columna) {
        return tablero[fila][columna];
    }

    public boolean esMovimientoValido(int fila, int columna, int valor) {
        for (int i = 0; i < 9; i++) {
            if (i != columna && tablero[fila][i] == valor) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (i != fila && tablero[i][columna] == valor) {
                return false;
            }
        }

        int inicioFila = fila / 3 * 3;
        int inicioColumna = columna / 3 * 3;
        for (int i = inicioFila; i < inicioFila + 3; i++) {
            for (int j = inicioColumna; j < inicioColumna + 3; j++) {
                if ((i != fila || j != columna) && tablero[i][j] == valor) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public boolean resolver() {
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                if (tablero[fila][columna] == 0) {
                    for (int numero = 1; numero <= 9; numero++) {
                        if (esMovimientoValido(fila, columna, numero)) {
                            tablero[fila][columna] = numero;

                            if (resolver()) {
                                return true;
                            } else {
                                tablero[fila][columna] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean esSolucionValida() {
        for (int i = 0; i < 9; i++) {
            boolean[] presentes = new boolean[9];
            for (int j = 0; j < 9; j++) {
                if (tablero[i][j] != 0) {
                    if (presentes[tablero[i][j] - 1]) {
                        return false;
                    }
                    presentes[tablero[i][j] - 1] = true;
                }
            }
        }

        for (int j = 0; j < 9; j++) {
            boolean[] presentes = new boolean[9];
            for (int i = 0; i < 9; i++) {
                if (tablero[i][j] != 0) {
                    if (presentes[tablero[i][j] - 1]) {
                        return false;
                    }
                    presentes[tablero[i][j] - 1] = true;
                }
            }
        }

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                boolean[] presentes = new boolean[9];
                for (int k = 0; k < 9; k++) {
                    int fila = i + k / 3;
                    int columna = j + k % 3;
                    if (tablero[fila][columna] != 0) {
                        if (presentes[tablero[fila][columna] - 1]) {
                            return false;
                        }
                        presentes[tablero[fila][columna] - 1] = true;
                    }
                }
            }
        }

        return true;
    }

    public int[] obtenerPista() {
        Integer[] numerosArray = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> numeros = new ArrayList<>(Arrays.asList(numerosArray));
        Collections.shuffle(numeros);

        List<int[]> celdasVacias = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tablero[i][j] == 0) {
                    celdasVacias.add(new int[] {i, j});
                }
            }
        }

        Collections.shuffle(celdasVacias);

        for (int[] celda : celdasVacias) {
            int fila = celda[0];
            int columna = celda[1];
            for (int valor : numeros) {
                if (esMovimientoValido(fila, columna, valor)) {
                    tablero[fila][columna] = valor;
                    if (esTableroResoluble()) {
                        return new int[] {fila, columna, valor};
                    }
                    tablero[fila][columna] = 0;
                }
            }
        }

        return null;
    }


    private boolean esTableroResoluble() {
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                if (tablero[fila][columna] == 0) {
                    for (int numero = 1; numero <= 9; numero++) {
                        if (esMovimientoValido(fila, columna, numero)) {
                            tablero[fila][columna] = numero;
                            if (esTableroResoluble()) {
                                tablero[fila][columna] = 0;
                                return true;
                            } else {
                                tablero[fila][columna] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean estaLleno() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tablero[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}

