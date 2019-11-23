package tests;

import odinas.CBuffer;

import java.lang.reflect.Field;
import java.util.Arrays;

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
        for (int i = 1; i < 15; i++) {
            var a = new CBuffer<Integer>(i);
            assertTrue(a.isEmpty());
        }
        var a = new CBuffer<Integer>(3);
        a.add(1);
        assertFalse(a.isEmpty());
        a.pop();
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
}