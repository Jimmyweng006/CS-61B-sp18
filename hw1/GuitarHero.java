public class GuitarHero {
    private static final double BASE_FREQUENCY = 440.0;
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        synthesizer.GuitarString stringCur = new synthesizer.GuitarString(BASE_FREQUENCY);
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int idx = KEYBOARD.indexOf(key);
                if (idx == -1) {
                    continue;
                }

                double curFrequency = BASE_FREQUENCY * Math.pow(2, ((idx - 24.0) / 12.0));
                stringCur = new synthesizer.GuitarString(curFrequency);
                stringCur.pluck();
            }

            double sample = stringCur.sample();

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            stringCur.tic();
        }
    }
}
