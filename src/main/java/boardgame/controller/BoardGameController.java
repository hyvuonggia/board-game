package boardgame.controller;

import boardgame.model.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardGameController {
    @FXML
    private GridPane board;

    BoardGameModel model = new BoardGameModel();

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private static List<Player> playerList;

    public static List<Player> getPlayerList() {
        return playerList;
    }

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    @FXML
    private Text playerOneNameText;

    @FXML
    private Text playerTwoNameText;

    @FXML
    private TextField stepsCountPlayer1TextField;

    @FXML
    private TextField stepsCountPlayer2TextField;


    @FXML
    private void initialize() {
        createBoard();
        createPieces();
        model.createPlayers();
        setSelectablePositions();
        showSelectablePositions();
        alterPlayer();
        addBindCountStep();
    }

    @FXML
    private void handleFinishButton(ActionEvent event) throws IOException {
        Logger.info("Clicked Finish button");
        setPlayerStepCount();
        generateScore();
        switchToScoreWindow(event);
    }

    private void setPlayerStepCount() {
        model.getPlayer1().setStepCount(model.getCountStepPlayer1());
        model.getPlayer2().setStepCount(model.getCountStepPlayer2());
    }


    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void createPieces() {
        for (int i = 0; i < model.getNumberOfPieces(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getPieceColor(i).name()));
            getSquare(model.getPiecePosition(i)).getChildren().add(piece);
        }
    }

    private Circle createPiece(Color color) {
        var piece = new Circle(50);
        piece.setFill(color);
        return piece;
    }

    private StackPane getSquare(Position position) {
        for (Node child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row()
                    && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.info("Clicked on square {}", position);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getPieceNumber(selected);
                    var direction = PieceDirection.of(position.row() - selected.row(), position.col() - selected.col());
                    Logger.debug("Moving piece {} {}", pieceNumber, direction);
                    model.move(pieceNumber, direction);
                    if (model.isRedWins()) {
                        Logger.info("RED WINS");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setHeaderText("RED WINS");
                        alert.showAndWait();
                    }
                    if (model.isBlueWins()) {
                        Logger.info("BLUE WINS");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setHeaderText("BLUE WINS");
                        alert.showAndWait();
                    }
                    deselectSelectedPosition();
                    alterSelectionPhase();
                }
            }
        }

    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (model.currentPlayer.get().equals(BoardGameModel.Player.PLAYER1)) {
                    selectablePositions.addAll(model.getSelectableRed());
                } else if (model.currentPlayer.get().equals(BoardGameModel.Player.PLAYER2)) {
                    selectablePositions.addAll(model.getSelectableBlue());
                }
            }
            case SELECT_TO -> {
                var pieceNumber = model.getPieceNumber(selected);
                for (var direction : model.getValidMoves(pieceNumber)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }


    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
    }

    public void setPlayerOneName(String name) {
        Logger.info("Setting player one's name to {}", name);
        this.playerOneNameText.setText(name);
        model.getPlayer1().setName(name);
    }

    public void setPlayerTwoName(String name) {
        Logger.info("Setting player two's name to {}", name);
        this.playerTwoNameText.setText(name);
        model.getPlayer2().setName(name);
    }

    private void alterPlayer() {
        model.currentPlayerProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    Logger.info("Switch to {}", newValue);
                }
        );

    }

    private void addBindCountStep() {
        stepsCountPlayer1TextField.textProperty().bind(model.countStepPlayer1Property().asString());
        stepsCountPlayer2TextField.textProperty().bind(model.countStepPlayer2Property().asString());
    }

    private void switchToScoreWindow(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/scoreui.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void generateScore() {
        Jdbi jdbi = Jdbi.create("jdbc:h2:file:./src/main/resources/playerdb");
        jdbi.installPlugin(new SqlObjectPlugin());

        try (Handle handle = jdbi.open()) {
            PlayerDao dao = handle.attach(PlayerDao.class);
            dao.createPlayerTable();

            dao.insertPlayer(model.getPlayer1());
            Logger.info("Added Player 1 into DATABASE");
            dao.insertPlayer(model.getPlayer2());
            Logger.info("Added Player 2 into DATABASE");

            playerList = dao.listPlayers();

        }
    }

}

