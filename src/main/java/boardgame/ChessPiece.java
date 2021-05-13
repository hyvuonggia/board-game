package boardgame;

import lombok.Data;

@Data
public class ChessPiece {
    private ChessColor chessColor;
    private int row;
    private int col;
}
