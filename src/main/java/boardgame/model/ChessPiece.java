package boardgame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChessPiece {
    private ChessColor chessColor;
    private int row;
    private int col;
}
