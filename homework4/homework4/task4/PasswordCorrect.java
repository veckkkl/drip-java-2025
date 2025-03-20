package homework4.task4;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PasswordCorrect {

    public static boolean containsRequiredSymbols(String password) {
        String regex = "[~!@#$%^&*|]";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        return matcher.find();
    }
}