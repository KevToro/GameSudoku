package com.example.game_sudoku;

import com.example.game_sudoku.View.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        GameStage.generateInstance();
    }
}
