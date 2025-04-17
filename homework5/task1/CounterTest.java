package homework5.task1;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CounterTest {

    private static final int THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 1000;

    @Test
    public void testSynchronizedCounter() throws InterruptedException {
        testCounter(new SynchronizedCounter());
    }

    @Test
    public void testAtomicCounter() throws InterruptedException {
        testCounter(new AtomicCounter());
    }

    @Test
    public void testReentrantLockCounter() throws InterruptedException {
        testCounter(new ReentrantLockCounter());
    }

    @Test
    public void testSemaphoreCounter() throws InterruptedException {
        testCounter(new SemaphoreCounter());
    }

    private void testCounter(Counter counter) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        for (int i = 0; i < THREADS; i++) {
            executor.execute(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    counter.increment();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals("Неверное значение счетчика",
                THREADS * INCREMENTS_PER_THREAD,
                counter.getValue());
    }
}
