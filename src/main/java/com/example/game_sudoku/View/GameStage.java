

package com.example.game_sudoku.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStage extends Stage {
    public GameStage() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/game_sudoku/game-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setTitle("GameSudoku");
        setScene(scene);
        setResizable(false);
        show();
    }
    public static  void clearGame (){
        GameContainer.INSTANCE.close();
        GameContainer.INSTANCE = null;
    }
    public static GameStage generateInstance () throws IOException{
        return GameContainer.INSTANCE = new GameStage();
    }
    private static class GameContainer {
        private static GameStage INSTANCE;

    }
}
