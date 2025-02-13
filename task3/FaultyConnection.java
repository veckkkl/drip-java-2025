package task3;

import java.util.Random;

public class FaultyConnection implements Connection {
    private static final Random random = new Random();

    @Override
    public void execute(String command) {
        if (random.nextBoolean()) {
            throw new ConnectionException("Failed to execute command: " + command);
        }
        System.out.println("Executing command: " + command);
    }

    @Override
    public void close() {
        System.out.println("FaultyConnection closed.");
    }
}