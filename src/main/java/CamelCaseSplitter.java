import java.util.Arrays;
import java.util.List;

public class CamelCaseSplitter {

    public static List<String> converterCamelCase(String original) {
        return Arrays.stream(original.split("((?=[A-Z]))"))
                .map(String::toLowerCase)
                .toList();
    }
}
