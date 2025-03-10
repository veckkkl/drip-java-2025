package homework3.task4;

public class RomanConverter {
    public static String convertToRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Введите число от 1 до 3999.");
        }

        int[] arabicNumbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arabicNumbers.length; i++) {
            while (num >= arabicNumbers[i]) {
                result.append(romanSymbols[i]);
                num -= arabicNumbers[i];
            }
        }
        return result.toString();
    }
}