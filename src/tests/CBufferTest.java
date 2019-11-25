package tests;

import odinas.CBuffer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CBufferTest {

    @org.junit.jupiter.api.Test
    void isFull() {
        var a = new CBuffer<Integer>(2);
        assertFalse(a.isFull());
        a.add(1);
        assertFalse(a.isFull());
        a.add(2);
        assertTrue(a.isFull());

        for (int i = 2; i < 30; i++) {
            a = new CBuffer<Integer>(i);
            for (int j = 0; j < i - 1; j++) {
                a.add(j);
                assertFalse(a.isFull());
            }
            a.add(5);
            assertTrue(a.isFull());
        }
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        var a = new CBuffer<Integer>(3);
        assertTrue(a.isEmpty());
        a.add(1);
        assertFalse(a.isEmpty());
        a.remove();
        assertTrue(a.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void add() throws NoSuchFieldException, IllegalAccessException {
        for (int i = 1; i < 100; i++) {
            var a = new CBuffer<Integer>(i);
            Field f = a.getClass().getDeclaredField("elements");
            Field f2 = a.getClass().getDeclaredField("tail");
            f.setAccessible(true);
            f2.setAccessible(true);

            for (int j = 1; j < i; j++) {
                Integer element = j;
                a.add(element);
                var elements = (Object[]) f.get(a);
                var tail = (int) f2.get(a);
                assertTrue(elements[tail].equals(element));
            }
        }
    }


    @Test
    void clear() {
        var a = new CBuffer<String>(3);
        assertTrue(a.isEmpty());
        a.add("asd");
        assertFalse(a.isEmpty());
        a.clear();
        assertTrue(a.isEmpty());
    }

    @Test
    void peek() throws NoSuchFieldException, IllegalAccessException {
        var a = new CBuffer<Integer>(3);
        assertNull(a.peek());
        a.add(5);
        a.add(6);
        var field = a.getClass().getDeclaredField("size");
        field.setAccessible(true);
        int size1 = (int) field.get(a);

        assertEquals(5, a.peek());
        var field2 = a.getClass().getDeclaredField("size");
        field2.setAccessible(true);
        int size2 = (int) field2.get(a);
        assertEquals(size1, size2);
    }
}