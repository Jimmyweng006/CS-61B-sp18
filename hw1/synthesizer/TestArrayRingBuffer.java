package synthesizer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void iteratorTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);

        List<Integer> expectedList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            expectedList.add(i);
        }
        List<Integer> actualList = new ArrayList<>();
        for (Integer x: arb) {
            actualList.add(x);
        }
        assertEquals(expectedList, actualList);
    }

    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        Integer expected = 1;
        assertEquals(expected, arb.peek());

        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        expected = 4;
        assertEquals(expected, (Integer) arb.fillCount());

        expected = 1;
        assertEquals(expected, arb.dequeue());

        arb.dequeue();
        expected = 3;
        assertEquals(expected, arb.peek());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
