package com.amrdevelopermessenger.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ReceiveMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        //receive message
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        int result = intent.getIntExtra("result",0);
        TextView textView = (TextView)findViewById(R.id.textview);
        //set text on textview
        textView.setText(message);


    }
}
