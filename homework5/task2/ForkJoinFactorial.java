package homework5.task2;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinFactorial implements FactorialCalculator {
    private static class FactorialTask extends RecursiveTask<BigInteger> {
        private final int start;
        private final int end;

        FactorialTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected BigInteger compute() {
            if (end - start <= 10) {
                BigInteger result = BigInteger.ONE;
                for (int i = start; i <= end; i++) {
                    result = result.multiply(BigInteger.valueOf(i));
                }
                return result;
            } else {
                int mid = (start + end) / 2;
                FactorialTask left = new FactorialTask(start, mid);
                FactorialTask right = new FactorialTask(mid + 1, end);
                left.fork();
                BigInteger rightResult = right.compute();
                BigInteger leftResult = left.join();
                return leftResult.multiply(rightResult);
            }
        }
    }

    @Override
    public BigInteger calculate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Число не должно быть отрицательным");
        }
        if (n == 0 || n == 1) {
            return BigInteger.ONE;
        }
        return ForkJoinPool.commonPool().invoke(new FactorialTask(2, n));
    }
}
