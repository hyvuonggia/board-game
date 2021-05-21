package boardgame.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceTest {

    ChessPiece piece;

    void assertPiece(int expectedRow, int expectedCol, ChessPiece piece){
        assertAll(
                () -> assertEquals(expectedRow, piece.getPosition().row()),
                () -> assertEquals(expectedCol, piece.getPosition().col())
        );
    }

    @BeforeEach
    void init() {
        piece = new ChessPiece(ChessColor.RED, new Position(0, 0));
    }

    @Test
    void moveTo_down_right() {
        piece.moveTo(PieceDirection.DOWN_RIGHT);
        assertPiece(1, 1, piece);
    }

    @Test
    void moveTo_down_left() {
        piece.moveTo(PieceDirection.DOWN_LEFT);
        assertPiece(1, -1, piece);
    }

    @Test
    void moveTo_up_right() {
        piece.moveTo(PieceDirection.UP_RIGHT);
        assertPiece(-1, 1, piece);
    }

    @Test
    void moveTo_up_left() {
        piece.moveTo(PieceDirection.UP_LEFT);
        assertPiece(-1, -1, piece);
    }

    @Test
    void testToString() {
        assertEquals("RED-(0, 0)", piece.toString());
    }
}