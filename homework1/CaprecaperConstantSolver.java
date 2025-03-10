import java.util.Arrays;

class CaprecaperConstantSolver {
    public static int countK(int number) {
        if (number == 6174) {
            return 0;
        }

        String numStr = String.format("%04d", number);

        char[] ascendingArr = numStr.toCharArray();
        Arrays.sort(ascendingArr);
        int ascending = Integer.parseInt(new String(ascendingArr));

        String descendingStr = new StringBuilder(new String(ascendingArr)).reverse().toString();
        int descending = Integer.parseInt(descendingStr);

        int nextNumber = descending - ascending;

        return 1 + countK(nextNumber);
    }
}