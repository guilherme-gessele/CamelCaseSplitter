import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CamelCaseSplitter {


    public static List<String> converterCamelCase(String original) {

        var strings = new ArrayList<String>();
        var isPreviousLowerCase = false;
        var builder = new StringBuilder();

        for (var i = 0; i < original.length(); i++) {

            var currentChar = original.charAt(i);

            if (i == original.length() - 1) {
                builder.append(currentChar);
                strings.add(builder.toString());
                continue;
            }

            if (isPreviousLowerCase && Character.isUpperCase(currentChar)) {
                isPreviousLowerCase = false;
                strings.add(builder.toString());

                builder = new StringBuilder().append(currentChar);
                continue;
            }

            if (Character.isLowerCase(currentChar)) {
                isPreviousLowerCase = true;
            }

            builder.append(currentChar);
        }

        return strings.stream()
                .map(s -> {
                    var containsLowerCase = s.chars().anyMatch(Character::isLowerCase);

                    if (Character.isUpperCase(s.charAt(0)) && containsLowerCase) {
                        return s.toLowerCase();
                    }

                    return s;
                })
                .toList();

    }
}
