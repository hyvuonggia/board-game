package boardgame.model;

/**
 * The interface that get the row and column change
 *
 */
public interface Direction {

    /**
     * Get the row changing.
     *
     * @return number of rows changed.
     */
    int getRowChange();

    /**
     * Get the column changing.
     *
     * @return number of columns changed.
     */
    int getColChange();
}
