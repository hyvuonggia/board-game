package boardgame.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class BoardGameController {
    @FXML
    private GridPane board;
    
    @FXML
    private void initialize(){
        createBoard();

    }

    private void createBoard(){
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare(){
        var square = new StackPane();
        square.getStyleClass().add("square");
        return square;
    }
}
