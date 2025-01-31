class DigitNumberCounter {
    public static int countDigits(int number) {
        if (number == 0) {
            return 1;
        }

        int k = 0;
        int num = Math.abs(number);
        while (num > 0) {
            num /= 10;
            k++;
        }
        return k;
    }
}
