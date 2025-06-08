package homework5.task1;

import java.util.concurrent.Semaphore;

public class SemaphoreCounter implements Counter {
    private int count = 0;
    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public void increment() {
        try {
            semaphore.acquire();
            count++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public int getValue() {
        try {
            semaphore.acquire();
            return count;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        } finally {
            semaphore.release();
        }
    }
}
