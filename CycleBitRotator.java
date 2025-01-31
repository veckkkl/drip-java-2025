class CycleBitRotator {

    public static int rotateRight(int number, int shift) {
        int bitLength = Integer.toBinaryString(number).length();
        shift = shift % bitLength;
        return ((number >>> shift) | (number << (bitLength - shift))) & ((1 << bitLength) - 1);

    }

    public static int rotateLeft(int number, int shift) {
        int bitLength = Integer.toBinaryString(number).length();
        shift = shift % bitLength;
        return ((number << shift) | (number >>> (bitLength - shift))) & ((1 << bitLength) - 1);
    }
}