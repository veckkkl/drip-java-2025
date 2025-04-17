package homework5.task1;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RaceConditionTest {

    private static class UnsafeCounter implements Counter {
        private int count = 0;

        @Override
        public void increment() {
            count++;
        }

        @Override
        public int getValue() {
            return count;
        }
    }

    @Test
    public void demonstrateRaceCondition() throws InterruptedException {
        final int THREADS = 10;
        final int INCREMENTS = 1000;
        Counter unsafeCounter = new UnsafeCounter();

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        for (int i = 0; i < THREADS; i++) {
            executor.execute(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    unsafeCounter.increment();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Небезапасное значение у счетчика: " + unsafeCounter.getValue() +
                " (ожидаемое значение: " + THREADS * INCREMENTS + ")");
    }
}
