package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

import java.util.*;

/**
 * Model implementation of the game.
 */
public class BoardGameModel {

    /**
     * Represents the size of the board.
     */
    public static final int BOARD_SIZE = 5;

    /**
     * Represents the array of pieces on the board.
     */
    public ChessPiece[] pieces;

    /**
     * Represents a list of positions that all red pieces need to stand in order to win the game.
     */
    private List<Position> redWinPositions = new ArrayList<>();

    /**
     * Represents a list of positions that all blue pieces need to stand in order to win the game.
     */
    private List<Position> blueWinPositions = new ArrayList<>();

    private boardgame.model.Player player1;
    private boardgame.model.Player player2;


    /**
     * Represents 2 player of the game.
     */
    public enum Player {
        PLAYER1,
        PLAYER2;

        /**
         * Alternate between two players.
         *
         * @return the next player.
         */
        public Player next() {
            return switch (this) {
                case PLAYER1 -> PLAYER2;
                case PLAYER2 -> PLAYER1;
            };
        }
    }


    /**
     * Represents the current player.
     */
    public ReadOnlyObjectWrapper<Player> currentPlayer = new ReadOnlyObjectWrapper<>();

    /**
     * Count the number of steps of {@code PLAYER1}
     */
    private ReadOnlyIntegerWrapper countStepPlayer1 = new ReadOnlyIntegerWrapper();

    /**
     * Count the number of steps of {@code PLAYER2}
     */
    private ReadOnlyIntegerWrapper countStepPlayer2 = new ReadOnlyIntegerWrapper();

    /**
     * Create {@code BoardGameModel} instance
     */
    public BoardGameModel() {
        this(
                new ChessPiece(ChessColor.BLUE, new Position(0, 0)),
                new ChessPiece(ChessColor.BLUE, new Position(0, 1)),
                new ChessPiece(ChessColor.BLUE, new Position(0, 2)),
                new ChessPiece(ChessColor.BLUE, new Position(0, 3)),
                new ChessPiece(ChessColor.BLUE, new Position(0, 4)),
                new ChessPiece(ChessColor.BLUE, new Position(1, 0)),
                new ChessPiece(ChessColor.BLUE, new Position(1, 4)),
                new ChessPiece(ChessColor.RED, new Position(4, 0)),
                new ChessPiece(ChessColor.RED, new Position(4, 1)),
                new ChessPiece(ChessColor.RED, new Position(4, 2)),
                new ChessPiece(ChessColor.RED, new Position(4, 3)),
                new ChessPiece(ChessColor.RED, new Position(4, 4)),
                new ChessPiece(ChessColor.RED, new Position(3, 0)),
                new ChessPiece(ChessColor.RED, new Position(3, 4))
        );
        currentPlayer.set(Player.PLAYER1);
        addGameOverPositions();
        countStepPlayer1.set(0);
        countStepPlayer2.set(0);
    }

    /**
     * Create {@code BoardGameModel} instance.
     *
     * @param pieces list of pieces to be added into the board.
     */
    public BoardGameModel(ChessPiece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }


    public boolean isOnBoard(Position position) {
        if (position.row() >= 0 && position.row() < BOARD_SIZE && position.col() >= 0 && position.col() < BOARD_SIZE) {
            return true;
        }
        return false;
    }

    /**
     * Get the position property.
     *
     * @param pieceNumber number of a piece.
     * @return {@code ObjectProperty<Position>} of a piece.
     */
    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

    /**
     * Check all pieces are not outside the board.
     *
     * @param pieces array of pieces that needs to check
     */
    private void checkPieces(ChessPiece[] pieces) {
        for (ChessPiece piece : pieces) {
            if (!isOnBoard(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        for (ChessPiece piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }


    /**
     * Get the color of the piece.
     *
     * @param pieceNumber number of a piece
     * @return color of the piece
     */
    public ChessColor getPieceColor(int pieceNumber) {
        return pieces[pieceNumber].getChessColor();
    }

    /**
     * Get the current position of the chess piece.
     *
     * @param pieceNumber number of a piece
     * @return current position of the piece
     */
    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    /**
     * Get number of pieces on the board.
     *
     * @return number of pieces
     */
    public int getNumberOfPieces() {
        return this.pieces.length;
    }

    /**
     * Check if the next move is still on the board and not previously occupied by another piece.
     *
     * @param pieceNumber number of the piece.
     * @param direction   direction of the next move.
     * @return {@code true} if the next move is inside the board and not previously occupied by another piece.
     * Otherwise {@code return} {@code false}.
     */
    private boolean isValidMove(int pieceNumber, PieceDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        if (!isOnBoard(newPosition)) {
            return false;
        }
        for (ChessPiece piece : pieces) {
            if (newPosition.equals(piece.getPosition())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get all the possible moves of a chess piece.
     *
     * @param pieceNumber number of the piece.
     * @return set of possible moves.
     */
    public Set<PieceDirection> getValidMoves(int pieceNumber) {
        Set<PieceDirection> validMoves = new HashSet<>();
        for (PieceDirection direction : PieceDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     * Get the number steps of {@code PLAYER1}
     *
     * @return number of steps
     */
    public int getCountStepPlayer1() {
        return countStepPlayer1.get();
    }

    /**
     * Get the number steps property of {@code PLAYER1}
     *
     * @return number of steps property
     */
    public ReadOnlyIntegerWrapper countStepPlayer1Property() {
        return countStepPlayer1;
    }

    /**
     * Set the number of steps of {@code PLAYER1}
     *
     * @param countStepPlayer1 number of steps of {@code PLAYER1}
     */
    public void setCountStepPlayer1(int countStepPlayer1) {
        this.countStepPlayer1.set(countStepPlayer1);
    }

    /**
     * Get the number steps of {@code PLAYER2}
     *
     * @return number of steps
     */
    public int getCountStepPlayer2() {
        return countStepPlayer2.get();
    }

    /**
     * Get the number steps property of {@code PLAYER2}
     *
     * @return number of steps property
     */
    public ReadOnlyIntegerWrapper countStepPlayer2Property() {
        return countStepPlayer2;
    }

    /**
     * Set the number of steps of {@code PLAYER2}
     *
     * @param countStepPlayer2 number of steps of {@code PLAYER2}
     */
    public void setCountStepPlayer2(int countStepPlayer2) {
        this.countStepPlayer2.set(countStepPlayer2);
    }

    /**
     * Represents the movement of a piece.
     *
     * @param pieceNumber number of a piece.
     * @param direction   direction of the move.
     */
    public void move(int pieceNumber, PieceDirection direction) {
        pieces[pieceNumber].moveTo(direction);
        if (currentPlayer.get().equals(Player.PLAYER1)) {
            setCountStepPlayer1(getCountStepPlayer1() + 1);
            Logger.info("Number of steps of PLAYER1: {}", countStepPlayer1);
        }
        if (currentPlayer.get().equals(Player.PLAYER2)) {
            setCountStepPlayer2(getCountStepPlayer2() + 1);
            Logger.info("Number of steps of PLAYER2: {}", countStepPlayer2);
        }
        currentPlayer.set(currentPlayer.get().next());
    }

    /**
     * Get positions of every pieces on board.
     *
     * @return list of positions.
     */
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            positions.add(piece.getPosition());
        }
        return positions;
    }

    /**
     * Get the piece number of specified position if there is. Otherwise returns -1.
     *
     * @param position of the piece.
     * @return the piece number.
     */
    public int getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Get the current player property.
     *
     * @return {@code ReadOnlyObjectWrapper<Player>} of the current player.
     */
    public ReadOnlyObjectWrapper<Player> currentPlayerProperty() {
        return currentPlayer;
    }

    private void addGameOverPositions() {
        for (var piece : pieces) {
            if (piece.getChessColor().equals(ChessColor.BLUE)) {
                redWinPositions.add(piece.getPosition());
            }
            if (piece.getChessColor().equals(ChessColor.RED)) {
                blueWinPositions.add(piece.getPosition());
            }
        }

//        redWinPositions.add(new Position(0,0));
//        redWinPositions.add(new Position(0,1));
//        redWinPositions.add(new Position(0,2));
//        redWinPositions.add(new Position(0,3));
//        redWinPositions.add(new Position(0,4));
//        redWinPositions.add(new Position(1,0));
//        redWinPositions.add(new Position(1,4));

//        blueWinPositions.add(new Position(4, 0));
//        blueWinPositions.add(new Position(4, 1));
//        blueWinPositions.add(new Position(4, 2));
//        blueWinPositions.add(new Position(4, 3));
//        blueWinPositions.add(new Position(4, 4));
//        blueWinPositions.add(new Position(3, 0));
//        blueWinPositions.add(new Position(3, 4));
    }

    /**
     * Get the list of red pieces that can be selected.
     *
     * @return list of red pieces that can be selected.
     */
    public List<Position> getSelectableRed() {
        List<Position> list = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            if (getRedValidMoves(getPieceNumber(piece.getPosition())).size() > 0){
                list.add(piece.getPosition());
            }
        }
        return list;
    }

    private boolean isValidMoveRed(int pieceNumber, PieceDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        if (pieces[pieceNumber].getChessColor().equals(ChessColor.RED)) {
            return isValidMove(pieceNumber, direction);
        } else {
            return false;
        }
    }

    private Set<PieceDirection> getRedValidMoves(int pieceNumber) {
        Set<PieceDirection> validMoves = new HashSet<>();
        for (PieceDirection direction : PieceDirection.values()) {
            if (isValidMoveRed(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     * Get the list of blue pieces that can be selected.
     *
     * @return list of blue pieces that can be selected.
     */
    public List<Position> getSelectableBlue() {
        List<Position> list = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            if (getBlueValidMoves(getPieceNumber(piece.getPosition())).size() > 0){
                list.add(piece.getPosition());
            }
        }
        return list;
    }

    private boolean isValidMoveBlue(int pieceNumber, PieceDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        if (pieces[pieceNumber].getChessColor().equals(ChessColor.BLUE)) {
            return isValidMove(pieceNumber, direction);
        } else {
            return false;
        }
    }

    private Set<PieceDirection> getBlueValidMoves(int pieceNumber) {
        Set<PieceDirection> validMoves = new HashSet<>();
        for (PieceDirection direction : PieceDirection.values()) {
            if (isValidMoveBlue(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    private List<Position> getRedPiecesPositions() {
        List<Position> redPiecesPositions = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            if (piece.getChessColor().equals(ChessColor.RED)) {
                redPiecesPositions.add(piece.getPosition());
            }
        }
        return redPiecesPositions;
    }

    /**
     * Check if all red pieces are in the win positions or not
     *
     * @return {@code true} if all red pieces are in the win positions,
     * {@code false} if at least one piece is still not in the win position
     */
    public boolean isRedWins() {
        List<Position> positions = getRedPiecesPositions();
        for (Position position : positions) {
            if (!redWinPositions.contains(position)) {
                return false;
            }
        }
        player1.setScore(player1.getScore() + 100);
        Logger.info("Player 1 took {} steps", player1.getStepCount());
        return true;
    }

    private List<Position> getBluePiecesPositions() {
        List<Position> bluePiecesPositions = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            if (piece.getChessColor().equals(ChessColor.BLUE)) {
                bluePiecesPositions.add(piece.getPosition());
            }
        }
        return bluePiecesPositions;
    }

    /**
     * Check if all blue pieces are in the win positions or not
     *
     * @return {@code true} if all blue pieces are in the win positions,
     * {@code false} if at least one piece is still not in the win position
     */
    public boolean isBlueWins() {
        List<Position> positions = getBluePiecesPositions();
        for (Position position : positions) {
            if (!blueWinPositions.contains(position)) {
                return false;
            }
        }
        player2.setScore(player2.getScore() + 100);
        Logger.info("Player 2 took {} steps", player2.getStepCount());
        return true;
    }

    /**
     * Get Player 1 instance.
     *
     * @return instance of Player 1
     */
    public boardgame.model.Player getPlayer1() {
        return player1;
    }

    /**
     * Get Player 2 instance.
     *
     * @return instance of Player 2
     */
    public boardgame.model.Player getPlayer2() {
        return player2;
    }

    /**
     * Create 2 players instances
     */
    public void createPlayers() {
        player1 = new boardgame.model.Player();
        player2 = new boardgame.model.Player();
    }

    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();
        System.out.println(model);
    }
}
