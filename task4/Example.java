package task4;

public class Example {

    public void exampleMethod() {
        CallingInfo info = StackTraceUtil.callingInfo();
        System.out.println("Class: " + info.className() + ", Method: " + info.methodName());
    }

    public static void main(String[] args) {
        Example example = new Example();
        example.exampleMethod();
    }
}
