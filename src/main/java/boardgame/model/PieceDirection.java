package boardgame.model;

public enum PieceDirection implements Direction{
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1)
    ;

    private final int rowChange;
    private final int colChange;

    PieceDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    @Override
    public int getRowChange() {
        return rowChange;
    }

    @Override
    public int getColChange() {
        return colChange;
    }

    public static PieceDirection of(int rowChange, int colChange){
        for (PieceDirection direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange){
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
