package task4;

public class StackTraceUtil {

    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        StackTraceElement caller = stackTrace[1];
        return new CallingInfo(caller.getClassName(), caller.getMethodName());
    }

    public static CallingInfo callingInfo(int depth) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (depth >= stackTrace.length) {
            throw new IllegalArgumentException("Depth exceeds stack trace size");
        }
        StackTraceElement caller = stackTrace[depth];
        return new CallingInfo(caller.getClassName(), caller.getMethodName());
    }
}
