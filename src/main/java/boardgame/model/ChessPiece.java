package boardgame.model;

import javafx.beans.property.ObjectProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChessPiece {
    private ChessColor chessColor;
    private final ObjectProperty position =
}
