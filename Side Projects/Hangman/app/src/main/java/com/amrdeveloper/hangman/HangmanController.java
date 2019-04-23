package com.amrdeveloper.hangman;

public class HangmanController {

    public boolean isValidCharacter(String trueWord, String character) {
        return trueWord.contains(character.toLowerCase());
    }

    public String createEncryptedWord(String word) {
        return word.replaceAll(".", "*");
    }

    public String showPlayerAnswer(String trueWord, String encWord, String character) {
        StringBuilder newWord = new StringBuilder();
        for (int i = 0; i < trueWord.length(); i++) {
            if (String.valueOf(trueWord.charAt(i)).equalsIgnoreCase(character)) {
                newWord.append(String.valueOf(trueWord.charAt(i)));
            } else {
                newWord.append(String.valueOf(encWord.charAt(i)));
            }
        }
        return newWord.toString();
    }
}
