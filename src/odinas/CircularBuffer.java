package odinas;

public interface CircularBuffer<T> {
    boolean isFull();
    boolean isEmpty();
    void add(T element);
    T pop();
    T peek();
    void clear();
}
