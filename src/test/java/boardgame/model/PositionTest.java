package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position position;

    void assertPosition(int expectedRow, int expectedCol, Position position){
        assertAll(
                () -> assertEquals(expectedRow, position.row()),
                () -> assertEquals(expectedCol, position.col())
        );
    }


    @BeforeEach
    void init(){
        position = new Position(0, 0);
    }

    @Test
    void moveTo_up_left() {
        assertPosition(-1, -1, position.moveTo(PieceDirection.UP_LEFT));
    }
    @Test
    void moveTo_up_right() {
        assertPosition(-1, 1, position.moveTo(PieceDirection.UP_RIGHT));
    }
    @Test
    void moveTo_down_left() {
        assertPosition(1, -1, position.moveTo(PieceDirection.DOWN_LEFT));
    }
    @Test
    void moveTo_down_right() {
        assertPosition(1, 1, position.moveTo(PieceDirection.DOWN_RIGHT));
    }

    @Test
    void testToString() {
        assertEquals("(0, 0)", position.toString());
    }
}