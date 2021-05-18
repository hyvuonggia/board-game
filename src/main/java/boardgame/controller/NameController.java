package boardgame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class NameController {

    @FXML
    private TextField playerOneNameTextField;

    @FXML
    private TextField playerTwoNameTextField;

    @FXML
    public void handlePlayGameButton(ActionEvent event) throws IOException {
        Logger.info("Clicked on Play Game button");
        Logger.info("Player one's name: {}", playerOneNameTextField.getText());
        Logger.info("Player two's name: {}", playerTwoNameTextField.getText());
        String playerOneName = playerOneNameTextField.getText();
        String playerTwoName = playerTwoNameTextField.getText();
        if (!playerOneName.equals("") && !playerTwoName.equals("")){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ui.fxml"));
            Parent root = fxmlLoader.load();
            BoardGameController boardGameController = fxmlLoader.getController();
            boardGameController.setPlayerOneName(playerOneName);
            boardGameController.setPlayerTwoName(playerTwoName);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }


}
