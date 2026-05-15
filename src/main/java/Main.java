import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 1000; i++) {
            executor.execute(Main::computeROccurrence);
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        Map.Entry<Integer, Integer> maxSizeToFreq = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue());
        System.out.println("Max sequence of " + maxSizeToFreq.getKey() + " Rs was found " + maxSizeToFreq.getValue() + " times.\n Other occurrences: ");
        for (var entry : sizeToFreq.entrySet()) {
            System.out.println(" - " + entry.getKey() + " (" + entry.getValue() + " times)");
        }

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    static void computeROccurrence() {
        final String seq = generateRoute("RLRFR", 100);
        int counter = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == 'R') {
                counter++;
            }
        }
        synchronized (sizeToFreq) {
            int currentFreq = sizeToFreq.getOrDefault(counter, 0);
            sizeToFreq.put(counter, ++currentFreq);
        }
    }
}