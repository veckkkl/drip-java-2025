package task3;

import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    private static final Random random = new Random();

    @Override
    public Connection getConnection() {
        if (random.nextDouble() < 0.75) {
            return new StableConnection();
        } else {
            return new FaultyConnection();
        }
    }
}
