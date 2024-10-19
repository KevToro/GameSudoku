package com.example.game_sudoku.Controller;

import com.example.game_sudoku.Model.Game;
import com.example.game_sudoku.View.GameStage;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Objects;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ButtonType;


public class GameController {
    Game game = new Game();
    TextField[][] textFields = new TextField[6][6];

    @FXML
    private GridPane GameGridPane;

    @FXML
    private void startNewGame () {
        System.out.println("Ejecuciòn");
        Alert alertReset = new Alert(Alert.AlertType.INFORMATION);
        alertReset.setHeaderText("¿Quieres reiniciar la partida?");
        alertReset.setContentText("Perderás todo el progreso actual, ¿estás seguro?");
        alertReset.setTitle("Confirmación de Reinicio");
        alertReset.showAndWait();
        if (alertReset.getResult() == ButtonType.OK) {
            game.resetPanel();// Si el usuario confirma, reinicia el tablero del modelo

            for (Node node : GameGridPane.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    int row = GridPane.getRowIndex(textField);
                    int col = GridPane.getColumnIndex(textField);
                    // Recorre los nodos, y obtiene su posicion en terminos de fila y columnas
                    if (!game.isPanel(row, col)) { //verifica si es editable
                        textField.setStyle(""); //elimina cualquier estilo aplicado al texto
                        textField.setText("");// Limpiar el contenido de  TextField
                    }
                }
            }
        }


    }
    @FXML
    private void showHelp () {
        Alert alertHelp = new Alert(Alert.AlertType.INFORMATION);
        alertHelp.setTitle("Help");
        alertHelp.setHeaderText("ingresa el numero sugerido en color verde");
        alertHelp.showAndWait();
        int [] emptyCell = locateEmptyCell ();
        if (emptyCell != null) {
            int row = emptyCell[0];
            int col = emptyCell[1];
            int suggestedNumber = findValidNumber (row, col);
            if (suggestedNumber != -1 ) {
                TextField suggestedField = textFields[row][col];
                suggestedField.setText(String.valueOf(suggestedNumber));
                suggestedField.setStyle("-fx-background-color: green;-fx-font-size: 30; -fx-font-weight: bold;");
            } else {
                displayAlert (" No hay sugerencias");
            }
        }else  {displayAlert("No hay celdas vacias disponibles");}
    }

    private int[] locateEmptyCell () {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (game.getNumber(i, j) == 0) { // si es 0 indica una celda vacía
                    return new int[]{i, j}; // Retorna la fila y columna
                }
            }
        }
        return null; // Retorna null si no hay celdas vacías
    } //busacmos celda vacia en el panel y alamacena ubicacion en localEmptyCell

    private int findValidNumber (int row, int col) { //sugiere numero valido que se pueda colocar en la celda
        for (int number = 1; number <=6; number++) { // iniciamos bucle
            if (game.isApproved (row, col,number )) { //llamamos metodo para comprobar si #actual es valdo
                return number; //retorna primer numero valido encontrado
            }
        }
        return -1;
    }


    private void displayAlert (String message) {  //usado para mostrar cuadro de dialogo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait(); //mostramos cuadro de alerta y esperamos a que el usuario lo cierre
    }

    @FXML
    private void exitApp () {
        System.exit(0); //cierra ventana
    }

    @FXML
    public void initialize(){
        for (int i=0; i<6; i++){
            for (int j=0; j<6; j++){ //comineza bucle que itera en filas y columnas
                TextField textField = new TextField();
                textField.setPrefWidth(80);
                textField.setPrefHeight(80);
                textField.setPadding(new Insets(0));
                textField.setAlignment(Pos.CENTER);
                textField.setStyle("-fx-font-size: 25; -fx-font-family: 'Times New Roman'; -fx-text-fill: orange");
                textField.setTranslateX(((int) (i/2))*10);
                textField.setTranslateY(((int) (j/3))*10);
                if(game.getNumber(i,j) != 0){ //comprueba si hay un # , si no es 0 significa que hay un umero preestablecido
                    textField.setText(String.valueOf(game.getNumber(i,j)));//Si hay un número preestablecido, se configura el texto del campo de texto a ese número
                    textField.setDisable(true);//Desactiva el campo de texto para que el usuario no pueda editarlo.
                    textField.setStyle("-fx-font-size: 35; -fx-font-family: 'Times New Roman'; -fx-text-fill: BLACK; -fx-font-weight: 600" );
                }
                textField.setOnKeyTyped(this::inputField);
                textFields[i][j] = textField;
                GameGridPane.add(textField, i, j);
            }
        }
    }
    private void inputField (KeyEvent event){
        TextField field = (TextField) event.getSource();
        if(field.getText() != ""){ //compreba si no esta vacio el campo de texto
            char textNumber = field.getText().charAt(0); //Toma el primer carácter del texto en el campo y lo almacena en textNumber
            field.setText(""); //limpia contenido
            if (Character.isDigit(textNumber)) { //verifica si el carcater ingresado es digito
                int number = Character.getNumericValue(textNumber); //convierte en entero
                if (number >= 1 && number <= 6) {  // comprueba si esta entre 1 y 6
                    field.setText(String.valueOf(number)); //Establece el texto del campo como el número ingresado
                    game.setNumber(number, getIndex(field)[0], getIndex(field)[1]);
                    verifyNumber(field, number, getIndex(field)[0], getIndex(field)[1]);
                    if (winVeri()) {
                        if (endValidation()) {
                            displayWinAlert();
                        }
                    }
                }
            }
        } else if(field.getText() == ""){
            game.setNumber(0,getIndex(field)[0], getIndex(field)[1]);
            nullStyle(field);
        }
    }
    private void nullStyle (TextField field){
        field.setStyle("-fx-font-size: 25; -fx-font-family: 'Times New Roman'; -fx-text-fill: orange");
    }
    private void errorStyle (TextField field){
        field.setStyle("-fx-font-size: 25; -fx-font-family: 'Times New Roman'; -fx-text-fill: black; -fx-background-color: red");
    }
    private int[] getIndex(TextField field){
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                if(textFields[i][j] == field){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0,0};
    }
    private void verifyNumber (TextField field, int number, int row, int column){
        boolean validNumber = true;
        for (int i=0; i<6; i++){
            if(i != row){
                if(number == game.getNumber(i, column)){
                    errorStyle(field);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Ingresa un valor valido");
                    alert.show();
                    validNumber = false;}
            }
        }for (int i=0; i<6; i++){
            if(i != column){
                if(number == game.getNumber(row, i)){
                    errorStyle(field);
                    validNumber = false;}
            }
        }
        int qRow = row/2;
        int qColumn = column/3;
        for (int i = qRow*2; i< (qRow + 1)*2; i++){
            for(int j = qColumn*3; j< (qColumn + 1)*3; j++){
                if(number == game.getNumber(i,j) && i != row && j != column){
                    errorStyle(field);
                    validNumber = false;}
            }
        }
        if (validNumber)
            nullStyle(field);
    }
    private boolean winVeri () {
        for (int i=0; i<6;i++){
            for(int j=0; j<6; j++){
                if (game.getNumber(i,j) == 0){
                    return false;
                }
            }
        }
        return true;
    }
    private boolean endValidation(){
        for(int i=0; i<6; i++){
            int checkRow = 0, checkColumn = 0;
            for (int j=0; j<6; j++){
                checkRow += game.getNumber(i,j);
                checkColumn += game.getNumber(j,i);
            }
            if(checkRow != 21 || checkColumn != 21)
                return false;
        }
        for (int bigI = 0; bigI < 3; bigI++){
            for (int bigJ = 0; bigJ < 2; bigJ++){
                int checkQuadrante = 0;
                for (int i = bigI*2; i< (bigI + 1)*2; i++) {
                    for (int j = bigJ * 3; j < (bigJ + 1) * 3; j++) {
                        checkQuadrante += game.getNumber(i,j);
                    }
                }
                if (checkQuadrante != 21)
                    return false;
            }
        }
        return true;
    }
    private void displayWinAlert (){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Victoria");
        alert.setHeaderText("Has Ganado");
        alert.setContentText("¡Todo el tablero está completo y correcto!");

        ButtonType newGame = new ButtonType("Nueva partida");
        ButtonType exitGame = new ButtonType("Salir del Juego");

        alert.getButtonTypes().setAll(newGame,exitGame);

        alert.setOnCloseRequest(dialogEvent -> {
            if (alert.getResult() == newGame){
                GameStage.clearGame();
                try {
                    GameStage.generateInstance();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                GameStage.clearGame();
            }
        });


        alert.showAndWait();
    }





}