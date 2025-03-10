package homework3.task1;

public class Atbash {

    public static String atbash(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char b = Character.isLowerCase(c) ? 'a' : 'A';
                char mirroredC = (char) (b + ('z' - Character.toLowerCase(c)));
                result.append(Character.isLowerCase(c) ? mirroredC : Character.toUpperCase(mirroredC));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
