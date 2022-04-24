package synthesizer;

import java.util.HashSet;
import java.util.Set;

//Make sure this class is public
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int size = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(size);
        for (int i = 0; i < size; i++) {
            buffer.enqueue((double) 0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        Set<Double> s = new HashSet<>();

        int size = buffer.fillCount();
        for (int i = 0; i < size; i++) {
            double r = Math.random() - 0.5;
            while (s.contains(r)) {
                r = Math.random() - 0.5;
            }

            s.add(r);
            buffer.dequeue();
            buffer.enqueue(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double front = buffer.dequeue();
        double nextFront = sample();

        double insertElement = DECAY * ((front + nextFront) / 2);
        buffer.enqueue(insertElement);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
