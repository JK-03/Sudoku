/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lab3;

/**
 *
 * @author jenniferbueso
 */
public interface Generable {
    void generar();
    void quitarNumerosAleatorios();
    boolean llenarTablero(int fila, int columna);
    boolean esMovimientoValido(int fila, int columna, int numero);
}
