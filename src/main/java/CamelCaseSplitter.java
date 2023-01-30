import java.util.ArrayList;
import java.util.List;

public class CamelCaseSplitter {


    public static List<String> converterCamelCase(String original) {

        var firstChar = original.charAt(0);
        if (Character.isDigit(firstChar)) {
            throw new IllegalArgumentException("Cannot start with Digit");
        }

        if (original.chars().anyMatch(c -> !(Character.isAlphabetic(c) || Character.isDigit(c)))){
            throw new IllegalArgumentException("Cannot have not alphabetic digits");
        }

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
                } else {
                    builder.append(currentChar);
                }
                continue;
            }

            if (Character.isLowerCase(currentChar)) {

                var nextChar = original.charAt(i + 1);
                var isNextCharUpperCase = Character.isUpperCase(nextChar);
                var isNextCharDigit = Character.isDigit(nextChar);

                builder.append(currentChar);
                if (isNextCharUpperCase || isNextCharDigit) {
                    strings.add(builder.toString());
                    builder = new StringBuilder();
                }
                continue;
            }

            if (Character.isDigit(currentChar)) {

                var nextChar = original.charAt(i + 1);
                var isNextCharDigit = Character.isDigit(nextChar);

                builder.append(currentChar);
                if (!isNextCharDigit) {
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
