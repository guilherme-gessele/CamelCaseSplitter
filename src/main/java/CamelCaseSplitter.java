import java.util.ArrayList;
import java.util.List;

import static java.util.function.Predicate.not;

public class CamelCaseSplitter {

    public static List<String> converterCamelCase(String original) {
        verifyIfStartsWithDigit(original);
        verifyOccurrenceOfInvalidChars(original);
        return convertCamelCase(original);
    }

    private static List<String> convertCamelCase(String original) {
        var splits = splitCamelCase(original);
        return splits.stream()
                .filter(not(String::isBlank))
                .map(CamelCaseSplitter::toLowerCaseIfNotAcronym)
                .toList();
    }

    private static String toLowerCaseIfNotAcronym(String s) {
        if (startsWithUpperCase(s) && containsLowerCaseCharacter(s)) {
            return s.toLowerCase();
        } else{
            return s;
        }
    }

    private static boolean containsLowerCaseCharacter(String s) {
        return s.chars().anyMatch(Character::isLowerCase);
    }

    private static boolean startsWithUpperCase(String s) {
        return Character.isUpperCase(s.charAt(0));
    }

    private static ArrayList<String> splitCamelCase(String original) {
        var strings = new ArrayList<String>();

        var builder = new StringBuilder();
        for (var i = 0; i < original.length(); i++) {
            builder = handleCharacter(original, strings, builder, i);
        }

        return strings;
    }

    private static StringBuilder handleCharacter(String original, ArrayList<String> strings, StringBuilder builder, int i) {
        
        var currentChar = original.charAt(i);
        if (isLastDigit(original, i)) {
            return handleLastCharacter(strings, builder, currentChar);
        } else if (verifyIfIsAlphabeticCharacter(currentChar)) {
            return handleAlphabeticCharacters(original, strings, builder, i, currentChar);
        } else {
            return handleNumericCharacters(original, strings, builder, i, currentChar);
        }
    }

    private static boolean verifyIfIsAlphabeticCharacter(char currentChar) {
        return Character.isAlphabetic(currentChar);
    }

    private static boolean isLastDigit(String original, int i) {
        return i == original.length() - 1;
    }

    private static void verifyIfStartsWithDigit(String original) {
        var firstChar = original.charAt(0);
        if (Character.isDigit(firstChar)) {
            throw new IllegalArgumentException("Cannot start with Digit");
        }
    }

    private static void verifyOccurrenceOfInvalidChars(String original) {
        if (original.chars().anyMatch(c -> !(Character.isAlphabetic(c) || Character.isDigit(c)))){
            throw new IllegalArgumentException("Cannot have not alphabetic digits");
        }
    }

    private static StringBuilder handleLastCharacter(ArrayList<String> strings, StringBuilder builder, char currentChar) {
        builder.append(currentChar);
        strings.add(builder.toString());
        return null;
    }

    private static StringBuilder handleUpperCaseCharacters(String original, ArrayList<String> strings, StringBuilder builder, int i, char currentChar) {
        var nextChar = original.charAt(i + 1);
        var isNextCharLowerCase = Character.isLowerCase(nextChar);

        if (isNextCharLowerCase) {
            strings.add(builder.toString());
            builder = new StringBuilder().append(currentChar);
        } else {
            builder.append(currentChar);
        }
        return builder;
    }

    private static StringBuilder handleLowerCaseCharacters(String original, ArrayList<String> strings, StringBuilder builder, int i, char currentChar) {
        var nextChar = original.charAt(i + 1);
        var isNextCharUpperCase = Character.isUpperCase(nextChar);
        var isNextCharDigit = Character.isDigit(nextChar);

        builder.append(currentChar);
        if (isNextCharUpperCase || isNextCharDigit) {
            strings.add(builder.toString());
            builder = new StringBuilder();
        }
        return builder;
    }

    private static StringBuilder handleAlphabeticCharacters(String original, ArrayList<String> strings, StringBuilder builder, int i, char currentChar) {
        if (Character.isUpperCase(currentChar)) {
            return handleUpperCaseCharacters(original, strings, builder, i, currentChar);
        } else {
            return handleLowerCaseCharacters(original, strings, builder, i, currentChar);
        }
    }

    private static StringBuilder handleNumericCharacters(String original, ArrayList<String> strings, StringBuilder builder, int i, char currentChar) {
        var nextChar = original.charAt(i + 1);
        var isNextCharDigit = Character.isDigit(nextChar);

        builder.append(currentChar);
        if (!isNextCharDigit) {
            strings.add(builder.toString());
            builder = new StringBuilder();
        }
        return builder;
    }
}
