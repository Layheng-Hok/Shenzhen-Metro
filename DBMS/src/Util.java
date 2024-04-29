import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Util {
    private static final List<Character> separators = new ArrayList<>(Arrays.asList('/', '\\', '-', '|', ',', '，', '、', ';', '；', ' ', ' ','.','。'));

    public static <T> List<T> readJsonArray(Path path, Class<T> clz) {
        try {
            String jsonStrings = Files.readString(path);
            return JSON.parseArray(jsonStrings, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSeparator(char c) {
        return separators.contains(c);
    }

    public static boolean containSeparator(String str) {
        for (char c : str.toCharArray())
            if (isSeparator(c))
                return true;
        return false;
    }

    public static boolean containOnlySeparators(String str) {
        for (char c : str.toCharArray())
            if (!isSeparator(c))
                return false;
        return true;
    }

    public static class UniqueOrderedSet<E> implements Iterable<E> {
        private Set<E> set;
        private List<E> list;

        public UniqueOrderedSet() {
            set = new HashSet<>();
            list = new ArrayList<>();
        }

        public void add(E element) {
            if (set.add(element)) {
                list.add(element);
            }
        }

        @Override
        public Iterator<E> iterator() {
            return list.iterator();
        }
    }

}
