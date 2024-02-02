/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JuegoSudoku {
    private SudokuVisual interfaz;
    private Tablero tablero;

    public JuegoSudoku() {
        tablero = new Tablero();
        interfaz = new SudokuVisual(tablero);
        interfaz.setVisible(true);
    }

    public void iniciarJuego() {
        tablero.generar();
        interfaz.actualizarInterfazConTablero();
        interfaz.setVisible(true);
        
        interfaz.botonVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarTablero();
            }
        });

        interfaz.botonPista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darPista();
            }
        });

        interfaz.botonResolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolver();
            }
        });

        interfaz.botonNuevoJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoJuego();
            }
        });

    }

    public void comprobarSolucion() {
        // Llenar el tablero con los valores ingresados por el usuario
        interfaz.llenarTableroDesdeInterfaz();

        // Comprobar si la solución es válida
        if (tablero.esSolucionValida()) {
            JOptionPane.showMessageDialog(interfaz, "¡Felicidades! Has resuelto el Sudoku.");
        } else {
            JOptionPane.showMessageDialog(interfaz, "Lo siento, esa no es la solución correcta. Sigue intentándolo.");
        }
    }

    public void mostrarPista() {
        // Obtener una pista
        int[] pista = tablero.obtenerPista();

        // Mostrar la pista en la interfaz
        if (pista != null) {
            interfaz.mostrarPista(pista);
        } else {
            JOptionPane.showMessageDialog(interfaz, "No se encontró ninguna pista.");
        }
    }
    
    public void verificarTablero() {
        if (tablero.estaLleno()) {
            interfaz.llenarTableroDesdeInterfaz();
            if (tablero.esSolucionValida()) {
                JOptionPane.showMessageDialog(null, "¡Felicidades! Has resuelto el Sudoku.");
            } else {
                JOptionPane.showMessageDialog(null, "Lo siento, esa no es la solución correcta.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El tablero no está completo. Completa todas las celdas antes de verificar.");
        }
    }


    public void darPista() {
        int[] pista = tablero.obtenerPista();
        if (pista != null) {
            interfaz.mostrarPista(pista);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo obtener una pista.");
        }
    }

    public void resolver() {
        if (tablero.resolver()) {
            interfaz.actualizarInterfazConSolucion();
            interfaz.repaint();  // Agregar esta línea
        } else {
            JOptionPane.showMessageDialog(null, "Este Sudoku no tiene solución.");
        }
    }


    public void nuevoJuego() {
        tablero.generar();
        interfaz.actualizarInterfazConTablero();
    }
}