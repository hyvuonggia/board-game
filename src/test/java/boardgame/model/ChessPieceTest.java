package boardgame.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceTest {

    Position position;

    void assertPosition(int expectRow, int expectCol, Position position) {
        assertAll(
                () -> assertEquals(expectRow, position.row()),
                () -> assertEquals(expectCol, position.col())
        );
    }

    @BeforeEach
    void init() {
        position = new Position(0, 0);
    }

    @Test
    void moveTo() {
        assertPosition(-1, -1, position.moveTo(PieceDirection.UP_LEFT));
        assertPosition(-1, 1, position.moveTo(PieceDirection.UP_RIGHT));
        assertPosition(1, -1, position.moveTo(PieceDirection.DOWN_LEFT));
        assertPosition(1, 1, position.moveTo(PieceDirection.DOWN_RIGHT));
    }

    @Test
    void testToString() {
        assertEquals("(0, 0)", position.toString());
    }
}