package boardgame.model;

/**
 * Represents four directions of a piece can move.
 */
public enum PieceDirection implements Direction{
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1);

    private final int rowChange;
    private final int colChange;

    /**
     * Create a {@code PieceDirection} instance.
     *
     * @param rowChange number of rows changed.
     * @param colChange number of columns changed.
     */
    PieceDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * Get the number of rows changed.
     *
     * @return number of rows changed.
     */
    @Override
    public int getRowChange() {
        return rowChange;
    }

    /**
     * Get the number of columns changed.
     *
     * @return number of columns changed.
     */
    @Override
    public int getColChange() {
        return colChange;
    }

    /**
     * Get the direction of {@code PieceDirection} class.
     *
     * @param rowChange number of rows changed.
     * @param colChange number of columns changed.
     * @return direction of the piece.
     * @throws IllegalArgumentException if {@code rowChange} and {@code colChange} don't have valid values.
     */
    public static PieceDirection of(int rowChange, int colChange){
        for (PieceDirection direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange){
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
