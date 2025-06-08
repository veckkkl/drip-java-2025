package homework5.task4;

import java.util.Random;

public class MonteCarloPiSingleThread {
    public static void main(String[] args) {
        long totalPoints = 100_000_000;
        long circlePoints = 0;

        Random random = new Random();
        long startTime = System.currentTimeMillis();

        for (long i = 0; i < totalPoints; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                circlePoints++;
            }
        }

        double piEstimate = 4.0 * circlePoints / totalPoints;
        long endTime = System.currentTimeMillis();

        System.out.println("Estimated Pi: " + piEstimate);
        System.out.println("Actual Pi:   " + Math.PI);
        System.out.println("Error:       " + Math.abs(piEstimate - Math.PI));
        System.out.println("Time taken:  " + (endTime - startTime) + " ms");
    }
}
