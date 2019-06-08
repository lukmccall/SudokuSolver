package pl.sudokusolver.solver.utility;

/**
 * @param <U> type of first element
 * @param <V> type of second element
 */
public class Pair<U, V> {

    /**
     * fist element
     */
    private U first;

    /**
     * second element
     */
    private V second;

    /**
     * Create new <code>Pair</code> from given parameters.
     * @param first  the first element
     * @param second the second element
     */
    public Pair(U first, V second) {

        this.first = first;
        this.second = second;
    }

    /**
     * @return first element
     */
    public U getFirst() {
        return first;
    }

    /**
     * @param first first element
     */
    public void setFirst(U first) {
        this.first = first;
    }

    /**
     * @return second element
     */
    public V getSecond() {
        return second;
    }

    /**
     * @param second second element
     */
    public void setSecond(V second) {
        this.second = second;
    }
}