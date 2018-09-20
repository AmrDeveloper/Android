package com.mymessenger.amrdeveloper.mymessenger;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Open Activity on other application from device
    // 1 - If no actions are able to handle the intent Throws ActivityNotFoundException
    // 2 - If just one activity is able to receive  the intent, Android tells that activity to start and passes it the intent.
    // 3 - If more than one activity is able to  receive the intent,Android displays an activity chooser dialog
    public void onSendMessage(View view){
        //send Send Action on intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        //Set intent type
        intent.setType("text/plain");
        //Edit Text to get Message
        EditText message = (EditText)findViewById(R.id.message);
        String mess = message.getText().toString();
        //Open Chooser Dialog
        Intent chooser = Intent.createChooser(intent,"Choose Messenger App ....");
        //Set body of Message
        intent.putExtra(Intent.EXTRA_TEXT,mess);
        //Start Activity
        try{
            startActivity(chooser);
        }
        //no action able to hundle the intent
        catch(ActivityNotFoundException noact)
        {
            Toast.makeText(this, "no Activity can send your message", Toast.LENGTH_SHORT).show();
        }

    }
}
