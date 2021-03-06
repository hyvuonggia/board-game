package boardgame.controller;

import boardgame.model.BoardGameModel;
import boardgame.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.List;

public class ScoreController {

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Player, String> name;

    @FXML
    private TableColumn<Player, Integer> stepCount;

    @FXML
    private TableColumn<Player, Integer> score;


    @FXML
    private void initialize(){
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        stepCount.setCellValueFactory(new PropertyValueFactory<>("stepCount"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        List<Player> players = BoardGameController.getPlayerList();
        ObservableList<Player> observableList = FXCollections.observableArrayList();
        observableList.addAll(players);
        tableView.setItems(observableList);
    }

    @FXML
    private void handleExitButton(ActionEvent event){
        Logger.info("Clicked on Exit button");
        System.exit(0);
    }

    @FXML
    private void handleRestartButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/nameui.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
