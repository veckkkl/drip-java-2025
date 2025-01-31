class PalindromSolver {
    public static boolean isPalindromeDescendant(int number) {
        String sizeStr = String.valueOf(number);

        while (sizeStr.length() > 1) {
            if (Palindrome(sizeStr)) {
                return true;
            }
            sizeStr = Predok(sizeStr);
        }

        return false;
    }

    private static boolean Palindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private static String Predok(String sizeStr) {
        if (sizeStr.length() < 2) return sizeStr;

        StringBuilder predok = new StringBuilder();
        for (int i = 0; i < sizeStr.length() - 1; i += 2) {
            int sum = Character.getNumericValue(sizeStr.charAt(i)) + Character.getNumericValue(sizeStr.charAt(i + 1));
            predok.append(sum);
        }
        return predok.toString();
    }
}