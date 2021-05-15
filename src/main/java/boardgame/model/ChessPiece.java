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
}
