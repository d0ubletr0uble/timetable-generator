package odinas;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class CBuffer<T> implements CircularBuffer<T> {
    private Object[] elements;
    private int head = -1;
    private int tail = -1;
    private int size = 0;

    public CBuffer(int maxSize) {
        elements = new Object[maxSize];
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
    public void add(T element) {
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
    public T pop() {
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
        return Arrays.toString(elements) + head + ' ' + tail;
    }
}