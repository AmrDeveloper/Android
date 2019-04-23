package com.amrdeveloper.hangman;

public class HangmanController {

    private boolean isValidCharacter(String trueWord, String character) {
        return trueWord.contentEquals(character);
    }

    public String checkPlayerAnswer(String trueWord, String encWord, String character) {
         boolean isValid  = isValidCharacter(trueWord,character);
         if(isValid){
             StringBuilder newWord = new StringBuilder();
             for(int i = 0 ; i < trueWord.length() ; i++){
                 if(String.valueOf(trueWord.charAt(i)).equalsIgnoreCase(character)){
                     newWord.append(String.valueOf(trueWord.charAt(i)));
                 }else{
                     newWord.append(String.valueOf(encWord.charAt(i)));
                 }
             }
             return newWord.toString();
         }else{
             return encWord;
         }
    }
}
