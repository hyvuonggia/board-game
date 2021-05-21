package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceDirectionTest {

    @Test
    void of() {
        assertEquals(PieceDirection.UP_LEFT, PieceDirection.of(-1, -1));
        assertEquals(PieceDirection.UP_RIGHT, PieceDirection.of(-1, 1));
        assertEquals(PieceDirection.DOWN_LEFT, PieceDirection.of(1, -1));
        assertEquals(PieceDirection.DOWN_RIGHT, PieceDirection.of(1, 1));
        assertThrows(IllegalArgumentException.class, () -> PieceDirection.of(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> PieceDirection.of(1, 0));
    }
}