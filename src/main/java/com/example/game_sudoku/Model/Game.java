package com.example.game_sudoku.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    ArrayList<int[]> sudokuPanel;
    private boolean[][] Panel ;
    public Game () {
        sudokuPanel = new ArrayList<>(Arrays.asList(
                new int[] {6, 0, 0, 3, 0, 1},
                new int[] {0, 5, 0, 0, 4, 2},
                new int[] {5, 0, 0, 4, 0, 3},
                new int[] {0, 0, 2, 1, 5, 0},
                new int[] {0, 6, 5, 0, 3, 0},
                new int[] {1, 0, 3, 0, 0, 6}));

        Panel = new boolean[][]{
                {false,true,true,false,true,false},
                {true,false,true,true,false,false},
                {false,true,true,false,true,false},
                {true,true,false,false,false,true},
                {true,false,false,true,false,true},
                {false,true,false,true,true,false}
        };
    }
    public int getNumber (int row, int column){
        return  sudokuPanel.get(row)[column];
    }
    public void setNumber (int row, int column, int number){
        sudokuPanel.get(row)[column] = number;
    }
    public void resetPanel () {
        for (int row =0; row < 6; row ++) {
            for (int col =0; col < 6; col ++) {
                if (!Panel[row][col]) {
                    //representa una celda vacia si no es editable
                    sudokuPanel.get(row)[col] = 0; //marcamos la tabla
                }
            }
        }
    }
    public boolean isPanel (int row, int column){
        return Panel[row][column];
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

        int qRow = (row / 2) * 2; // Fila superior izquierda del cuadrante
        int qCol = (col / 3) * 2; // Columna superior izquierda del cuadrante
        for (int i=qRow; i < qRow+2; i++ ) {
            for (int j=qCol; j < qCol+3; j++ ) {
                if (getNumber(i,j) == number) {
                    return false; //Ya esta en el cuadrante
                }
            }
        }
        return true; // El numero se puede poner en la celda
    }
}


