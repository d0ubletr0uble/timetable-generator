package odinas;

/**
 * FIFO data structure
 * @param <T> Type param
 */
public interface CircularBuffer<T> {
    /**
     * @return true if buffer is full.
     */
    boolean isFull();

    /**
     * @return true if buffer is empty
     */
    boolean isEmpty();

    /**
     * adds element to the end
     * @param element element to add
     */
    void add(T element);

    /**
     * Removes element at head position (FIFO)
     * @return removed element or null if buffer is empty.
     */
    T remove();

    /**
     * Doesn't remove element, just returns it
     * @return element at head position or null if buffer is empty.
     */
    T peek();

    /**
     * Clears buffer
     */
    void clear();

    /**
     * @param element element to search for
     * @return true if buffer contains this element, false otherwise
     */
    boolean contains(T element);
}
