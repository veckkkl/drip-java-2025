class BrokenStringRepair {
    public static String fixString(String s) {
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length - 1; i += 2) {
            char temp = chars[i];
            chars[i] = chars[i + 1];
            chars[i + 1] = temp;
        }

        return new String(chars);
    }
}