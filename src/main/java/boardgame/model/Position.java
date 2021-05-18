package boardgame.model;

/**
 * The class that represent the position
 */
public record Position(int row, int col) {

    /**
     * Get the position that a piece would like to move to.
     *
     * @param direction direction that a piece moves.
     * @return the position after moving.
     */
    public Position moveTo(Direction direction){
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", row, col);
    }
}
