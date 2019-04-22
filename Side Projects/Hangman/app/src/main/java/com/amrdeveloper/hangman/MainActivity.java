package com.amrdeveloper.hangman;

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

    private CharactersGridAdapter adapter;

    private static final String INVALID_NUMBER_FORMAT = "Attempts Number : %d";
    private static final int INVALID_ANSWERS_NUMBER = 5;
    private int currentInvalidAnswers = INVALID_ANSWERS_NUMBER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        List<String> alphabetChars = AlphabetGenerator.getAlphabetChars();
        adapter = new CharactersGridAdapter(this, alphabetChars);
        charsGridView.setAdapter(adapter);
        charsGridView.setOnItemClickListener(onCharItemClickListener);

        attemptsNumberTxt.setText(String.format(Locale.ENGLISH, INVALID_NUMBER_FORMAT, currentInvalidAnswers));
        hangmanWordTxt.setText("******");
    }

    private void initViews() {
        charsGridView = findViewById(R.id.charsGridView);
        attemptsNumberTxt = findViewById(R.id.attemptsNumberTxt);
        hangmanWordTxt = findViewById(R.id.hangmanWordTxt);
    }

    private AdapterView.OnItemClickListener onCharItemClickListener = (parent, view, position, id) -> {
        View currentChar = adapter.getView(position, view, parent);
        Toast.makeText(this, "Char : " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
    };
}
