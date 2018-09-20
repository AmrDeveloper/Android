package com.amrdeveloper.myconstraintlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText subjectEditText;
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        subjectEditText = findViewById(R.id.subjectEditText);
        messageEditText = findViewById(R.id.messageEditText);
    }

    public void sendMessageIntent(View view){
        //Create Intent
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        //Set Intent Type
        emailIntent.setType("text/plain");

        //Set Intent Information
        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{emailEditText.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectEditText.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, messageEditText.getText().toString());

        //Start Intent with Intent Chooser
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Send email..."));

        }
    }
}
