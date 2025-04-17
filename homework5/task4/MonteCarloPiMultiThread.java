package homework5.task4;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ThreadLocalRandom;

public class MonteCarloPiMultiThread {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long totalPoints = 1_000_000_000;
        int numThreads = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicLong totalCirclePoints = new AtomicLong(0);

        long pointsPerThread = totalPoints / numThreads;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                long circlePoints = 0;
                ThreadLocalRandom random = ThreadLocalRandom.current();

                for (long j = 0; j < pointsPerThread; j++) {
                    double x = random.nextDouble();
                    double y = random.nextDouble();
                    if (x * x + y * y <= 1.0) {
                        circlePoints++;
                    }
                }
                totalCirclePoints.addAndGet(circlePoints);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        double piEstimate = 4.0 * totalCirclePoints.get() / totalPoints;
        long endTime = System.currentTimeMillis();

        System.out.println("Threads used: " + numThreads);
        System.out.println("Estimated Pi: " + piEstimate);
        System.out.println("Actual Pi:   " + Math.PI);
        System.out.println("Error:       " + Math.abs(piEstimate - Math.PI));
        System.out.println("Time taken:  " + (endTime - startTime) + " ms");
    }
}
