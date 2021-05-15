package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ChessPiece {

    private ChessColor chessColor;
    private ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public ChessColor getChessColor() {
        return chessColor;
    }

    public Position getPosition() {
        return position.get();
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    public ChessPiece(ChessColor chessColor, Position position) {
        this.chessColor = chessColor;
        this.position.set(position);
    }

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
