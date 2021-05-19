package boardgame.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.tinylog.Logger;

public class ScoreController {

    @FXML
    public void handleExitButton(ActionEvent event){
        Logger.info("Clicked on Exit button");
        Platform.exit();
    }
}
