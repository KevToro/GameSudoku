package com.example.game_sudoku.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*public class Game {
    ArrayList<int[]> sudokuPanel;
    private boolean[][] Panel ;
    public Game () {
        sudokuPanel = new ArrayList<>(Arrays.asList(
                new int[] {6, 0, 0, 3, 0, 1},
                new int[] {0, 5, 0, 0, 4, 2},
                new int[] {5, 0, 0, 4, 0, 3},
                new int[] {0, 0, 2, 1, 5, 0},
                new int[] {0, 6, 5, 0, 3, 0},
                new int[] {1, 0, 3, 0, 0, 6}));*/

public class Game {
    private static final int SIZE = 6;
    private static final int SUBGRID_ROWS = 2;
    private static final int SUBGRID_COLS = 3;
    private ArrayList<int[]> sudokuPanel;
    private boolean[][] panel;

    // Tableros predefinidos
    private static final int[][][] PREDEFINED_BOARDS = {
            {
                    {6, 0, 0, 3, 0, 1},
                    {0, 5, 0, 0, 4, 2},
                    {5, 0, 0, 4, 0, 3},
                    {0, 0, 2, 1, 5, 0},
                    {0, 6, 5, 0, 3, 0},
                    {1, 0, 3, 0, 0, 6}
            },
            {
                    {0, 1, 0, 0, 2, 3},
                    {2, 0, 3, 1, 0, 0},
                    {3, 0, 0, 2, 0, 1},
                    {0, 3, 2, 0, 1, 0},
                    {1, 0, 0, 3, 0, 2},
                    {0, 0, 1, 0, 0, 3}
            },
            // Agrega más tableros aquí
    };



    public Game() {
        sudokuPanel = new ArrayList<>();
        panel = new boolean[][]{
                {false,true,true,false,true,false},
                {true,false,true,true,false,false},
                {false,true,true,false,true,false},
                {true,true,false,false,false,true},
                {true,false,false,true,false,true},
                {false,true,false,true,true,false}
        };
        generateSudoku(); //Genara un tablero al crear la partida
       // removeNumbers();
    }

    public int getNumber (int row, int column){
        return  sudokuPanel.get(row)[column];
    }
    public void setNumber(int row, int column, int number) {
        if (row < 0 || row >= SIZE || column < 0 || column >= SIZE) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + row + ", " + column);
        }
        // Suponiendo que sudokuPanel es una lista de arreglos.
        sudokuPanel.get(row)[column] = number;
    }
    public void resetPanel () {
        for (int row =0; row < 6; row ++) {
            for (int col =0; col < 6; col ++) {
                if (!panel[row][col]) {
                    //representa una celda vacia si no es editable
                    sudokuPanel.get(row)[col] = 0; //marcamos la tabla
                }
            }
        }
    }
    public boolean isPanel (int row, int column){
        return panel[row][column];
        //verifica si una celda es editable, devolviendo true o false
    }
    public boolean isApproved (int row, int col, int number){
        for (int i=0; i<6; i++) {
            if (getNumber(row,i) == number) {
                return false; //Revisa si el número ya está presente en la fila especificada. Si lo encuentra, devuelve false
            }
        }
        for (int i=0; i<6; i++) {
            if (getNumber(i,col) == number) {
                return false; //revisa si el número ya está en la columna.
            }
        }

        int qRow = (row / SUBGRID_ROWS) * SUBGRID_ROWS;
        int qCol = (col / SUBGRID_COLS) * SUBGRID_COLS;
        for (int i = qRow; i < qRow + SUBGRID_ROWS; i++) {
            for (int j = qCol; j < qCol + SUBGRID_COLS; j++) {
                if (getNumber(i, j) == number) {
                    return false; // Verifica cuadrante
                }
            }
        }
        return true; // El numero se puede poner en la celda
    }

    private void generateSudoku() {
        Random rand = new Random();
        int boardIndex = rand.nextInt(PREDEFINED_BOARDS.length);
        int[][] selectedBoard = PREDEFINED_BOARDS[boardIndex];

        // Limpia el sudokuPanel antes de llenarlo
        sudokuPanel.clear();
        for (int i = 0; i < SIZE; i++) {
            sudokuPanel.add(selectedBoard[i].clone()); // Clona el tablero seleccionado
        }
    }
    private boolean fillBoard () {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (sudokuPanel.get(row)[col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isApproved(row, col, num)) {
                            sudokuPanel.get(row)[col] = num;
                            if (fillBoard()) {
                                return true;
                            }
                            sudokuPanel.get(row)[col] = 0; // Deshacer
                        }
                    }
                    return false; // No se pudo llenar
                }
            }
        }
        printBoard(); // Imprimir el tablero al completar una fila o columna
        return true; // Tablero completo
    }
    private void printBoard() {
        for (int row = 0; row < SIZE; row++) {
            System.out.println(Arrays.toString(sudokuPanel.get(row)));
        }
        System.out.println("-----------");
    }
    private void removeNumbers() {
        Random rand = new Random();
        int count = rand.nextInt(21) + 40; // Eliminar entre 40 y 60 números
        while (count > 0) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (sudokuPanel.get(row)[col] != 0) {
                sudokuPanel.get(row)[col] = 0; // Vaciar celda
                count--;
            }
        }
    }
}


