package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChessPiece {

    private ChessColor chessColor;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

}
