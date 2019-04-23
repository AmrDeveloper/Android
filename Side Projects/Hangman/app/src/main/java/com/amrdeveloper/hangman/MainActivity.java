package com.amrdeveloper.hangman;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView attemptsNumberTxt;
    private TextView hangmanWordTxt;
    private GridView charsGridView;

    private WordsLoader wordsLoader;
    private HangmanController hangmanController;
    private CharactersGridAdapter adapter;

    private String encryptedWord = "";
    private String currentTrueWords = "";
    private int currentInvalidAnswers = INVALID_ANSWERS_NUMBER;

    private static final int INVALID_ANSWERS_NUMBER = 5;
    private static final String INVALID_NUMBER_FORMAT = "Attempts Number : %d";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        List<String> alphabetChars = AlphabetGenerator.getAlphabetChars();

        wordsLoader = new WordsLoader(this);
        hangmanController = new HangmanController();

        currentTrueWords = wordsLoader.getRandomWord();
        encryptedWord = hangmanController.createEncryptedWord(currentTrueWords);
        adapter = new CharactersGridAdapter(this, alphabetChars);

        charsGridView.setAdapter(adapter);
        charsGridView.setOnItemClickListener(onCharItemClickListener);

        attemptsNumberTxt.setText(String.format(Locale.ENGLISH, INVALID_NUMBER_FORMAT, currentInvalidAnswers));
        hangmanWordTxt.setText(encryptedWord);
    }

    private void initViews() {
        charsGridView = findViewById(R.id.charsGridView);
        attemptsNumberTxt = findViewById(R.id.attemptsNumberTxt);
        hangmanWordTxt = findViewById(R.id.hangmanWordTxt);
    }

    private AdapterView.OnItemClickListener onCharItemClickListener = (parent, view, position, id) -> {
        View currentCharView = adapter.getView(position, view, parent);
        makeViewClicked(currentCharView);

        String currentCharStr = adapter.getItem(position);
        boolean isValidAnswer = hangmanController.isValidCharacter(currentTrueWords,currentCharStr);
        if(isValidAnswer){
            Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
            String newWord = hangmanController.showPlayerAnswer(currentTrueWords,encryptedWord,currentCharStr);
            encryptedWord = newWord;
            hangmanWordTxt.setText(encryptedWord);
        }else{
            Toast.makeText(this, "InValid", Toast.LENGTH_SHORT).show();
            currentInvalidAnswers--;
            attemptsNumberTxt.setText(String.format(Locale.ENGLISH, INVALID_NUMBER_FORMAT, currentInvalidAnswers));
        }
    };

    private void makeViewClicked(View view){
        view.setClickable(false);
        view.setBackgroundColor(Color.GRAY);
    }
}
