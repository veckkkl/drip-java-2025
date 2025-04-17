package homework4.task5;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CarNumberCorrect {

    public static boolean isValidNumber(String plate) {
        String regex = "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(plate);

        return matcher.matches();
    }
}
