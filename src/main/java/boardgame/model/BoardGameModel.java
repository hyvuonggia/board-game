package boardgame.model;

public class BoardGameModel {
    public static final int BOARD_SIZE = 5;

    public ChessPiece[] pieces;

    public BoardGameModel() {
        this(new ChessPiece(ChessColor.BLUE, new Position(0, 0)),
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
    }

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

    public void checkPieces(ChessPiece[] pieces){
        for (ChessPiece piece : pieces) {
            if (!isOnBoard(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
        }
    }


}
