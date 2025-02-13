package task3;

public class StableConnection implements Connection {
    @Override
    public void execute(String command) {
        System.out.println("Executing command: " + command);
    }

    @Override
    public void close() {
        System.out.println("StableConnection closed.");
    }
}