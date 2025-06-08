package homework5.task1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getValue() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}