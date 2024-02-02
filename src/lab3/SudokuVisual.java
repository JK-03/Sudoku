/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jenniferbueso
 */
public class SudokuVisual extends JFrame {
    private Tablero tableroSudoku;
    public JButton botonVerificar, botonPista, botonResolver, botonNuevoJuego;
    private JTextField[][] campos;

    public SudokuVisual(Tablero tablero) {
        this.tableroSudoku = tablero;
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Agregar título en la parte superior
        JLabel titulo = new JLabel("Sudoku", JLabel.CENTER);
        titulo.setFont(new Font("Avenir Next Condensed", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        botonVerificar = new JButton("Verificar");
        botonPista = new JButton("Pista");
        botonResolver = new JButton("Resolver");
        botonNuevoJuego = new JButton("Nuevo Juego");

        // Mejorar el aspecto de los botones
        botonVerificar.setPreferredSize(new Dimension(120, 40));
        botonPista.setPreferredSize(new Dimension(120, 40));
        botonResolver.setPreferredSize(new Dimension(120, 40));
        botonNuevoJuego.setPreferredSize(new Dimension(120, 40));

        Font botonFont = new Font("Avenir Next Condensed", Font.BOLD, 16);
        botonVerificar.setFont(botonFont);
        botonPista.setFont(botonFont);
        botonResolver.setFont(botonFont);
        botonNuevoJuego.setFont(botonFont);

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonVerificar);
        panelBotones.add(botonPista);
        panelBotones.add(botonResolver);
        panelBotones.add(botonNuevoJuego);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panelBotones, BorderLayout.SOUTH);

        JPanel panelSudoku = new JPanel(new GridLayout(3, 3));
        panelSudoku.setBackground(Color.WHITE);
        panelSudoku.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        KeyAdapter soloNumeros = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                JTextField campo = (JTextField) e.getSource();
                if (!Character.isDigit(c) || campo.getText().length() >= 1) {
                    e.consume();
                }
            }
        };

        campos = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            JPanel panel = new JPanel(new GridLayout(3, 3));
            panel.setBorder(BorderFactory.createMatteBorder(2, 2, 1, 1, Color.BLACK));
            for (int j = 0; j < 9; j++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(new Font("Avenir Next Condensed", Font.BOLD, 40));
                field.addKeyListener(soloNumeros);
                panel.add(field);
                campos[i][j] = field;
            }
            panelSudoku.add(panel);
        }

        add(panelSudoku, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    public void llenarTableroDesdeInterfaz() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField celda = campos[i][j];
                String texto = celda.getText();
                if (!texto.isEmpty()) {
                    int valor = Integer.parseInt(texto);
                    tableroSudoku.establecerCelda(i, j, valor);
                }
            }
        }
    }

    public void actualizarInterfazConTablero() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valor = tableroSudoku.obtenerCelda(i, j);
                if (valor != 0) {
                    campos[i][j].setText(Integer.toString(valor));
                    campos[i][j].setForeground(Color.BLUE);  // Cambiar el color de los números a azul
                    campos[i][j].setEditable(false);
                } else {
                    campos[i][j].setText("");
                    campos[i][j].setEditable(true);
                }
            }
        }
    }

    public void actualizarInterfazConSolucion() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valor = tableroSudoku.obtenerCelda(i, j);
                campos[i][j].setText(Integer.toString(valor));
            }
        }
        repaint();
    }

    public void mostrarPista(int[] pista) {
        int fila = pista[0];
        int columna = pista[1];
        int valor = pista[2];
        campos[fila][columna].setText(Integer.toString(valor));
    }
    
}