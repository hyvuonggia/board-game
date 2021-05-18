package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represent a chess piece
 */
public class ChessPiece {

    /**
     * represent color of a chess piece
     */
    private ChessColor chessColor;

    /**
     * represent the position of a chess piece
     */
    private ObjectProperty<Position> position = new SimpleObjectProperty<>();

    /**
     * Get the color of a chess piece
     *
     * @return color of a chess piece
     */
    public ChessColor getChessColor() {
        return chessColor;
    }

    /**
     * Get the position of a chess piece
     *
     * @return the position of a piece
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * Return the position property of a piece
     *
     * @return the position property of a piece
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    /**
     * Create a {@code ChessPiece} object that represent a chess piece
     *
     * @param chessColor color of the piece
     * @param position position of the piece
     */
    public ChessPiece(ChessColor chessColor, Position position) {
        this.chessColor = chessColor;
        this.position.set(position);
    }

    /**
     * Represent the move action of a piece
     *
     * @param direction direction of the movement
     */
    public void moveTo(Direction direction){
        Position newPosition = this.position.get().moveTo(direction);
        this.position.set(newPosition);
    }

    @Override
    public String toString() {
        return String.format("%s-%s", this.chessColor, this.getPosition());
    }

//    public static void main(String[] args) {
//        ChessPiece piece = new ChessPiece(ChessColor.BLUE, new Position(0, 0));
//        piece.positionProperty().addListener((observableValue, oldPosition, newPosition) -> {
//            System.out.printf("%s -> %s\n", oldPosition.toString(), newPosition.toString());
//        });
//        System.out.println(piece);
//        piece.moveTo(PieceDirection.DOWN_RIGHT);
//        System.out.println(piece);
//        piece.moveTo(PieceDirection.UP_RIGHT);
//        System.out.println(piece);
//        piece.moveTo(PieceDirection.DOWN_LEFT);
//        System.out.println(piece);
//        piece.moveTo(PieceDirection.UP_LEFT);
//        System.out.println(piece);
//    }
}
