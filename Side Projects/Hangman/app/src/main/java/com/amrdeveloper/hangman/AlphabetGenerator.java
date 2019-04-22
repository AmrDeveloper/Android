package com.amrdeveloper.hangman;

import java.util.ArrayList;
import java.util.List;

public class AlphabetGenerator {

    private static List<String> alphabetChars;

    public static List<String> getAlphabetChars() {
        if (alphabetChars == null) {
            alphabetChars = new ArrayList<>();
            for (int i = 'A'; i <= 'Z'; i++) {
                alphabetChars.add(String.valueOf((char) i));
            }
        }
        return alphabetChars;
    }
}
