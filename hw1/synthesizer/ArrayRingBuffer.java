package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }

        rb[last] = x;
        this.fillCount++;
        last++;
        last %= capacity();
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }

        T ret = rb[first];
        rb[first] = null;
        this.fillCount--;
        first++;
        first %= capacity();

        return ret;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }

        return rb[first];
    }

    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator<T> implements Iterator<T> {
        private int pos;

        public QueueIterator() {
            pos = first;
        }

        public boolean hasNext() {
            return pos < last;
        }

        public T next() {
            T ret = (T) rb[pos];
            pos++;

            return ret;
        }
    }
}
