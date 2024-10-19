module com.example.game_sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.game_sudoku to javafx.fxml;
    exports com.example.game_sudoku;
    exports com.example.game_sudoku.Controller;
    opens com.example.game_sudoku.Controller to javafx.fxml;
}