import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Thread watcher = new Thread(Main::waiterThread);
        List<Thread> threadList = new ArrayList<>();
        watcher.start();
        for (int i = 0; i < 1000; i++) {
            threadList.add(new Thread(Main::computeROccurrence));
            threadList.getLast().start();
        }
        for (var thread : threadList) {
            thread.join();
        }
        watcher.interrupt();
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
            sizeToFreq.notify();
        }
    }

    static void waiterThread() {
        while(!Thread.interrupted() ) {
            synchronized (sizeToFreq) {
                try {
                    sizeToFreq.wait();
                    if (!sizeToFreq.isEmpty()) {
                        Map.Entry<Integer, Integer> entry = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue());
                        System.out.format("At that moment the biggest amount of Rs %d occurred %d times\n", entry.getKey(), entry.getValue());
                    }
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }
        }
    }
}