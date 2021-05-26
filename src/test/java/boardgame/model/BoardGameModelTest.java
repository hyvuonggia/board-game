package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    BoardGameModel model;

    @BeforeEach
    void init(){
        model = new BoardGameModel();
    }

    @Test
    void testToString() {
        String expected = """
                BLUE-(0, 0)
                BLUE-(0, 1)
                BLUE-(0, 2)
                BLUE-(0, 3)
                BLUE-(0, 4)
                BLUE-(1, 0)
                BLUE-(1, 4)
                RED-(4, 0)
                RED-(4, 1)
                RED-(4, 2)
                RED-(4, 3)
                RED-(4, 4)
                RED-(3, 0)
                RED-(3, 4)""";
        assertEquals(expected, model.toString());
    }


    @Test
    void getPieceColor() {
        assertEquals(ChessColor.BLUE, model.getPieceColor(0));
        assertEquals(ChessColor.BLUE, model.getPieceColor(1));
        assertEquals(ChessColor.BLUE, model.getPieceColor(2));
        assertEquals(ChessColor.BLUE, model.getPieceColor(3));
        assertEquals(ChessColor.BLUE, model.getPieceColor(4));
        assertEquals(ChessColor.BLUE, model.getPieceColor(5));
        assertEquals(ChessColor.BLUE, model.getPieceColor(6));
        assertEquals(ChessColor.RED, model.getPieceColor(7));
        assertEquals(ChessColor.RED, model.getPieceColor(8));
        assertEquals(ChessColor.RED, model.getPieceColor(9));
        assertEquals(ChessColor.RED, model.getPieceColor(10));
        assertEquals(ChessColor.RED, model.getPieceColor(11));
        assertEquals(ChessColor.RED, model.getPieceColor(12));
        assertEquals(ChessColor.RED, model.getPieceColor(13));
    }

    @Test
    void getPiecePosition() {
        assertEquals(new Position(0, 0), model.getPiecePosition(0));
        assertEquals(new Position(0, 1), model.getPiecePosition(1));
        assertEquals(new Position(0, 2), model.getPiecePosition(2));
        assertEquals(new Position(0, 3), model.getPiecePosition(3));
        assertEquals(new Position(0, 4), model.getPiecePosition(4));
        assertEquals(new Position(1, 0), model.getPiecePosition(5));
        assertEquals(new Position(1, 4), model.getPiecePosition(6));
        assertEquals(new Position(4, 0), model.getPiecePosition(7));
        assertEquals(new Position(4, 1), model.getPiecePosition(8));
        assertEquals(new Position(4, 2), model.getPiecePosition(9));
        assertEquals(new Position(4, 3), model.getPiecePosition(10));
        assertEquals(new Position(4, 4), model.getPiecePosition(11));
        assertEquals(new Position(3, 0), model.getPiecePosition(12));
        assertEquals(new Position(3, 4), model.getPiecePosition(13));
    }

    @Test
    void getNumberOfPieces() {
        assertEquals(14, model.getNumberOfPieces());
    }

    @Test
    void getValidMoves() {
        assertEquals(Set.of(PieceDirection.DOWN_RIGHT), model.getValidMoves(0));
        assertEquals(Set.of(PieceDirection.DOWN_RIGHT), model.getValidMoves(1));
        assertEquals(Set.of(PieceDirection.DOWN_RIGHT, PieceDirection.DOWN_LEFT), model.getValidMoves(2));
        assertEquals(Set.of(PieceDirection.DOWN_LEFT), model.getValidMoves(3));
        assertEquals(Set.of(PieceDirection.DOWN_LEFT), model.getValidMoves(4));
        assertEquals(Set.of(PieceDirection.DOWN_RIGHT), model.getValidMoves(5));
        assertEquals(Set.of(PieceDirection.DOWN_LEFT), model.getValidMoves(6));
        assertEquals(Set.of(PieceDirection.UP_RIGHT), model.getValidMoves(7));
        assertEquals(Set.of(PieceDirection.UP_RIGHT), model.getValidMoves(8));
        assertEquals(Set.of(PieceDirection.UP_RIGHT, PieceDirection.UP_LEFT), model.getValidMoves(9));
        assertEquals(Set.of(PieceDirection.UP_LEFT), model.getValidMoves(10));
        assertEquals(Set.of(PieceDirection.UP_LEFT), model.getValidMoves(11));
        assertEquals(Set.of(PieceDirection.UP_RIGHT), model.getValidMoves(12));
        assertEquals(Set.of(PieceDirection.UP_LEFT), model.getValidMoves(13));
    }

    @Test
    void getCountStepPlayer1() {
        assertEquals(0, model.getCountStepPlayer1());
    }


    @Test
    void setCountStepPlayer1() {
        model.setCountStepPlayer1(10);
        assertEquals(10, model.getCountStepPlayer1());
    }

    @Test
    void getCountStepPlayer2() {
        assertEquals(0, model.getCountStepPlayer2());
    }

    @Test
    void setCountStepPlayer2() {
        model.setCountStepPlayer2(11);
        assertEquals(11, model.getCountStepPlayer2());
    }

    @Test
    void move() {
        model.move(0, PieceDirection.DOWN_RIGHT);
        assertEquals(0, model.getPieceNumber(new Position(1, 1)));

        model.move(1, PieceDirection.DOWN_RIGHT);
        assertEquals(1, model.getPieceNumber(new Position(1, 2)));

        model.move(2, PieceDirection.DOWN_LEFT);
        assertEquals(-1, model.getPieceNumber(new Position(1,3)));
    }

    @Test
    void getPiecePositions() {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 0));
        positions.add(new Position(0, 1));
        positions.add(new Position(0, 2));
        positions.add(new Position(0, 3));
        positions.add(new Position(0, 4));
        positions.add(new Position(1, 0));
        positions.add(new Position(1, 4));
        positions.add(new Position(4, 0));
        positions.add(new Position(4, 1));
        positions.add(new Position(4, 2));
        positions.add(new Position(4, 3));
        positions.add(new Position(4, 4));
        positions.add(new Position(3, 0));
        positions.add(new Position(3, 4));

        assertEquals(positions, model.getPiecePositions());
    }

    @Test
    void getPieceNumber() {
        assertEquals(0, model.getPieceNumber(new Position(0, 0)));
        assertEquals(1, model.getPieceNumber(new Position(0, 1)));
        assertEquals(2, model.getPieceNumber(new Position(0, 2)));
        assertEquals(3, model.getPieceNumber(new Position(0, 3)));
        assertEquals(4, model.getPieceNumber(new Position(0, 4)));
        assertEquals(5, model.getPieceNumber(new Position(1, 0)));
        assertEquals(6, model.getPieceNumber(new Position(1, 4)));

        assertEquals(7, model.getPieceNumber(new Position(4, 0)));
        assertEquals(8, model.getPieceNumber(new Position(4, 1)));
        assertEquals(9, model.getPieceNumber(new Position(4, 2)));
        assertEquals(10, model.getPieceNumber(new Position(4, 3)));
        assertEquals(11, model.getPieceNumber(new Position(4, 4)));
        assertEquals(12, model.getPieceNumber(new Position(3, 0)));
        assertEquals(13, model.getPieceNumber(new Position(3, 4)));
    }

    @Test
    void isRedWins() {
        assertFalse(model.isRedWins());
    }

    @Test
    void isBlueWins() {
        assertFalse(model.isBlueWins());
    }

    @Test
    void getSelectableRed() {
        List<Position> list = new ArrayList<>();
        list.add(new Position(4, 0));
        list.add(new Position(4, 1));
        list.add(new Position(4, 2));
        list.add(new Position(4, 3));
        list.add(new Position(4, 4));
        list.add(new Position(3, 0));
        list.add(new Position(3, 4));

        assertEquals(list, model.getSelectableRed());
    }

    @Test
    void getSelectableBlue() {
        List<Position> list = new ArrayList<>();
        list.add(new Position(0, 0));
        list.add(new Position(0, 1));
        list.add(new Position(0, 2));
        list.add(new Position(0, 3));
        list.add(new Position(0, 4));
        list.add(new Position(1, 0));
        list.add(new Position(1, 4));

        assertEquals(list, model.getSelectableBlue());
    }
}