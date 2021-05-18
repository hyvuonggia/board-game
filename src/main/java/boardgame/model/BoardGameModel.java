package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

import java.util.*;

public class BoardGameModel {
    public static final int BOARD_SIZE = 5;

    public ChessPiece[] pieces;

    private List<Position> redWinPositions = new ArrayList<>();

    private List<Position> blueWinPositions = new ArrayList<>();

    public enum Player{
        PLAYER1,
        PLAYER2;

        public Player next(){
            return switch (this){
                case PLAYER1 -> PLAYER2;
                case PLAYER2 -> PLAYER1;
            };
        }
    }

    private ReadOnlyObjectWrapper<Player> currentPlayer = new ReadOnlyObjectWrapper<>();

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
    }

    public BoardGameModel(ChessPiece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }

    private boolean isOnBoard(Position position) {
        if (position.row() >= 0 && position.row() < BOARD_SIZE && position.col() >= 0 && position.col() < BOARD_SIZE) {
            return true;
        }
        return false;
    }

    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

    /**
     * Check all pieces are not outside the board
     *
     * @param pieces
     */
    public void checkPieces(ChessPiece[] pieces) {
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
     * Get the color of the piece
     *
     * @param pieceNumber
     * @return color of the piece
     */
    public ChessColor getPieceColor(int pieceNumber) {
        return pieces[pieceNumber].getChessColor();
    }

    /**
     * Get the current position of the chess piece
     *
     * @param pieceNumber
     * @return current position of the piece
     */
    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    /**
     * Get number of pieces on the board
     *
     * @return number of pieces
     */
    public int getNumberOfPieces() {
        return this.pieces.length;
    }

    /**
     * Check if the next move is still on the board and not previously occupied by another piece
     *
     * @param pieceNumber
     * @param direction
     * @return
     */
    public boolean isValidMove(int pieceNumber, PieceDirection direction) {
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
     * Get all the possible moves of a chess piece
     *
     * @param pieceNumber
     * @return set of possible moves
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

    public void move(int pieceNumber, PieceDirection direction) {
        pieces[pieceNumber].moveTo(direction);
        currentPlayer.set(currentPlayer.get().next());
    }

    /**
     * Get positions of every pieces on board
     *
     * @return list of positions
     */
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            positions.add(piece.getPosition());
        }
        return positions;
    }

    /**
     * Get the piece number of specified position if there is. Otherwise returns -1
     *
     * @param position of the piece
     * @return the piece number
     */
    public int getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return i;
            }
        }
        return -1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer.get();
    }

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
    }

    public List<Position> getRedPiecesPositions() {
        List<Position> redPiecesPositions = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            if (piece.getChessColor().equals(ChessColor.RED)) {
                redPiecesPositions.add(piece.getPosition());
            }
        }
        return redPiecesPositions;
    }

    public boolean isRedWins() {
        List<Position> positions = getRedPiecesPositions();
        for (Position position : positions) {
            if (!redWinPositions.contains(position)) {
                return false;
            }
        }
        return true;
    }

    public List<Position> getBluePiecesPositions() {
        List<Position> bluePiecesPositions = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            if (piece.getChessColor().equals(ChessColor.BLUE)) {
                bluePiecesPositions.add(piece.getPosition());
            }
        }
        return bluePiecesPositions;
    }

    public boolean isBlueWins() {
        List<Position> positions = getBluePiecesPositions();
        for (Position position : positions) {
            if (!blueWinPositions.contains(position)) {
                return false;
            }
        }
        return true;
    }




}
