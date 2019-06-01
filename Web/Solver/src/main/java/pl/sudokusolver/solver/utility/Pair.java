package pl.sudokusolver.solver.utility;

/**
 * Stryktura danych przechowująca parę elementów.
 * @param <U> typ pierwszego element
 * @param <V> typ drugiego element
 */
public class Pair<U, V> {

    /**
     * Pierwszy element <code>Pair</code>.
     */
    private U first;

    /**
     * Drugi element <code>Pair</code>.
     */
    private V second;

    /**
     * Tworzenie nowej <code>Pair</code> z podanymi wartościami.
     *
     * @param first  the first element
     * @param second the second element
     */
    public Pair(final U first, final V second) {

        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public void setFirst(U first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }
}
