package homework3.task7;

import java.util.Comparator;
import java.util.TreeMap;

public class TreeMapNull {
    static class NullFriendlyComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            if (s1 == null && s2 == null) return 0;
            if (s1 == null) return -1;
            if (s2 == null) return 1;
            return s1.compareTo(s2);
        }
    }

    public static void main(String[] args) {
        TreeMap<String, String> tree = new TreeMap<>(new NullFriendlyComparator());
        tree.put(null, "test");
        tree.put("Ivan", "human");
        tree.put("Moscow", "city");
        assert tree.containsKey(null) : "Null-ключ не найден";
        System.out.println(tree);
    }
}
