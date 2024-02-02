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
public class Tablero {
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

    private void quitarNumerosAleatorios() {
        // Dejar solo un número en cada fila, columna y bloque
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (random.nextInt(3) != 0) {  // Cambiado a random.nextInt(3)
                    tablero[i][j] = 0;
                }
            }
        }
    }

    private boolean llenarTablero(int fila, int columna) {
        if (fila == 9) {
            return true;  // Hemos llenado todo el tablero
        }

        int siguienteFila = (columna == 8) ? fila + 1 : fila;
        int siguienteColumna = (columna + 1) % 9;

        int[] numeros = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(numeros);  // Método para barajar el array

        for (int numero : numeros) {
            if (esMovimientoValido(fila, columna, numero)) {
                tablero[fila][columna] = numero;
                if (llenarTablero(siguienteFila, siguienteColumna)) {
                    return true;
                }
                tablero[fila][columna] = 0;  // Deshacer el cambio si no podemos continuar
            }
        }

        return false;  // No pudimos encontrar un valor válido para esta celda
    }

    // Método para barajar el array
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
        // Comprobar la fila
        for (int i = 0; i < 9; i++) {
            if (i != columna && tablero[fila][i] == valor) {
                return false;
            }
        }

        // Comprobar la columna
        for (int i = 0; i < 9; i++) {
            if (i != fila && tablero[i][columna] == valor) {
                return false;
            }
        }

        // Comprobar el cuadrante
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
                // Buscamos una celda vacía
                if (tablero[fila][columna] == 0) {
                    // Intentamos números del 1 al 9
                    for (int numero = 1; numero <= 9; numero++) {
                        if (esMovimientoValido(fila, columna, numero)) {
                            // Si el número es válido, lo colocamos en la celda
                            tablero[fila][columna] = numero;

                            // Continuamos con el siguiente número
                            if (resolver()) {
                                return true;
                            } else {
                                // Si no podemos continuar, deshacemos el cambio y probamos con otro número
                                tablero[fila][columna] = 0;
                            }
                        }
                    }

                    // Si no encontramos un número válido, volvemos atrás
                    return false;
                }
            }
        }

        // Si todas las celdas están llenas, hemos resuelto el Sudoku
        return true;
    }

    public boolean esSolucionValida() {
        // Comprobar cada fila
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

        // Comprobar cada columna
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

        // Comprobar cada cuadrante
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

        // Crear una lista de todas las celdas vacías
        List<int[]> celdasVacias = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tablero[i][j] == 0) {
                    celdasVacias.add(new int[] {i, j});
                }
            }
        }

        // Mezclar la lista de celdas vacías
        Collections.shuffle(celdasVacias);

        // Buscar una pista en las celdas vacías
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

        return null;  // No se encontró ninguna pista
    }


    private boolean esTableroResoluble() {
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                if (tablero[fila][columna] == 0) {
                    for (int numero = 1; numero <= 9; numero++) {
                        if (esMovimientoValido(fila, columna, numero)) {
                            tablero[fila][columna] = numero;
                            if (esTableroResoluble()) {
                                tablero[fila][columna] = 0;  // Deshacer el cambio si no podemos continuar
                                return true;
                            } else {
                                tablero[fila][columna] = 0;  // Deshacer el cambio si no podemos continuar
                            }
                        }
                    }
                    return false;  // No pudimos encontrar un valor válido para esta celda
                }
            }
        }
        return true;  // Todas las celdas están llenas, por lo que el tablero es resoluble
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

