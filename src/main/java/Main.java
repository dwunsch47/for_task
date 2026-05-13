import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        List<Future<Integer>> futureList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (String text : texts) {
            Callable<Integer> tester = () -> {
                return checkOneText(text);
            };
            futureList.add(executor.submit(tester));
        }
        executor.shutdown();

        int maxInterval = 0;
        long startTs = System.currentTimeMillis(); // start time
        for (Future<Integer> future : futureList) {
            int tmp = future.get();
            maxInterval = Math.max(tmp, maxInterval);
        }
        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Max interval: " + maxInterval);
        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static int checkOneText(String text) {
            int maxSize = 0;
            for (int i = 0; i < text.length(); i++) {
                for (int j = 0; j < text.length(); j++) {
                    if (i >= j) {
                        continue;
                    }
                    boolean bFound = false;
                    for (int k = i; k < j; k++) {
                        if (text.charAt(k) == 'b') {
                            bFound = true;
                            break;
                        }
                    }
                    if (!bFound && maxSize < j - i) {
                        maxSize = j - i;
                    }
                }
            }
            return maxSize;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}