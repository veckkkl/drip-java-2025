package homework5.task1;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter {
    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public void increment() {
        count.incrementAndGet();
    }

    @Override
    public int getValue() {
        return count.get();
    }
}
