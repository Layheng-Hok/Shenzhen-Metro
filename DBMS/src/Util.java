import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    private static final List<Character> seperators = new ArrayList<>(Arrays.asList(',', '，', '、', ' ',' '));

    public static <T> List<T> readJsonArray(Path path, Class<T> clz) {
        try {
            String jsonStrings = Files.readString(path);
            return JSON.parseArray(jsonStrings, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSeperator(char c) {
        return seperators.contains(c);
    }
}
