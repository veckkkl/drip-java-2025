package homework3.task2;

import java.util.ArrayList;
import java.util.List;

public class Clusterize {
    public static List<String> clusterize(String s) {
        List<String> clusters = new ArrayList<>();
        int b = 0;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                b++;
            } else if (c == ')') {
                b--;
            }

            if (b == 0) {
                clusters.add(s.substring(start, i + 1));
                start = i + 1;
            }
        }
        return clusters;
    }
}
