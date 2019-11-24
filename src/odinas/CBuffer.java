package odinas;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CBuffer<T> implements CircularBuffer<T>, Iterable<T> {
    private Object[] elements;
    private int head = -1;
    private int tail = -1;
    private int size = 0;

    public CBuffer(int maxSize) {
        elements = new Object[maxSize];
    }

    public CBuffer(T[] elements) {
        this.elements = elements;
        head = tail = 0;
        size = elements.length;
    }

    @Override
    public boolean isFull() {
        return size == elements.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(T element) throws IndexOutOfBoundsException {
        if (isFull())
            throw new IndexOutOfBoundsException("Buffer is full");
        else if (tail == -1) {
            tail = head = 0;
            elements[0] = element;
            size++;
        } else {
            tail = (tail + 1) % elements.length;
            elements[tail] = element;
            size++;
        }
    }

    @Override
    public T remove() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException("Buffer is empty");
        else {
            var element = elements[head];
            head = (head + 1) % elements.length;
            size--;
            return (T) element;
        }
    }

    @Override
    public T peek() {
        if (!isEmpty())
            return (T) elements[head];

        return null;
    }

    @Override
    public void clear() {
        tail = head = size = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    @Override
    public Iterator<T> iterator() {
        return new InfiniteIterator();
    }

    /**
     * Never ending looping iterator (used for calculating timetables or shifts in fabrics, etc.)
     */
    private class InfiniteIterator implements Iterator<T> {
        private int i = -1;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            i = (i + 1) % elements.length;
            return (T) elements[i];
        }
    }
}