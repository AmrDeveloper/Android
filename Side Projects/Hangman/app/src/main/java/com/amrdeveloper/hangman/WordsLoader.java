package com.amrdeveloper.hangman;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class WordsLoader {

    private Context context;
    private static String[] wordArray;
    private static final Random random = new Random();
    private static final int WORDS_NUMBER = 55900;
    private static final String WORDS_ASSETS_PATH = "words.txt";

    public static final String TAG = WordsLoader.class.getSimpleName();

    public WordsLoader(Context context) {
        this.context = context;
    }

    private void loadWordsString() {
        try {
            InputStream inputStream = context.getAssets().open(WORDS_ASSETS_PATH);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine = reader.readLine();
            while (currentLine != null) {
                stringBuilder.append(currentLine);
                currentLine = reader.readLine();
            }
            wordArray = stringBuilder.toString().split(" ");
        } catch (IOException ioEx) {
            Log.e(TAG, ioEx.getMessage());
        }
    }

    public String getRandomWord() {
        if (wordArray == null) {
            loadWordsString();
        }
        int index = random.nextInt(WORDS_NUMBER + 1);
        return wordArray[index];
    }
}

