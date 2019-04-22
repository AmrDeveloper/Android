package com.amrdeveloper.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView charsGridView;
    private static final int INVALID_ANSWERS_NUMBER = 5;
    private int currentInvalidAnswers = INVALID_ANSWERS_NUMBER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        charsGridView = findViewById(R.id.charsGridView);
        List<String> alphabetChars = AlphabetGenerator.getAlphabetChars();
        CharactersGridAdapter adapter = new CharactersGridAdapter(this,alphabetChars);
        charsGridView.setAdapter(adapter);
    }
}
