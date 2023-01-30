import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CamelCaseSplitter {


    public static List<String> converterCamelCase(String original) {

        var strings = new ArrayList<String>();
        var builder = new StringBuilder();

        for (var i = 0; i < original.length(); i++) {

            var currentChar = original.charAt(i);

            if (i == original.length() - 1) {
                builder.append(currentChar);
                strings.add(builder.toString());
                continue;
            }

            if (Character.isUpperCase(currentChar)) {

                var nextChar = original.charAt(i + 1);
                var isNextCharLowerCase = Character.isLowerCase(nextChar);

                if (isNextCharLowerCase) {
                    strings.add(builder.toString());
                    builder = new StringBuilder().append(currentChar);
                    continue;
                } else {
                    builder.append(currentChar);
                    continue;
                }
            }

            if (Character.isLowerCase(currentChar)) {

                var nextChar = original.charAt(i + 1);
                var isNextCharUpperCase = Character.isUpperCase(nextChar);
                var isNextCharDigit = Character.isDigit(nextChar);

                if (isNextCharUpperCase || isNextCharDigit) {
                    builder.append(currentChar);
                    strings.add(builder.toString());
                    builder = new StringBuilder();
                    continue;
                } else {
                    builder.append(currentChar);
                    continue;
                }
            }

            if (Character.isDigit(currentChar)) {

                var nextChar = original.charAt(i + 1);
                var isNextCharDigit = Character.isDigit(nextChar);

                if (isNextCharDigit) {
                    builder.append(currentChar);
                } else {
                    builder.append(currentChar);
                    strings.add(builder.toString());
                    builder = new StringBuilder();
                }
            }
        }

        return strings.stream()
                .filter(s -> s.length() > 0)
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
