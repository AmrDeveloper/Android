package com.amrdevelopermessenger.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //Call onSendMessage
    public void onSendMessage(View view){
        //Take Message from edittext
        EditText message = (EditText)findViewById(R.id.message);
        String msg = message.getText().toString();
        //Using Intent to send msg to next activity
        //take two parameters
        //this activity and next activity
        Intent intent = new Intent(this , ReceiveMessage.class);
        //put Extra String take tag or id and value
        intent.putExtra("message",msg);
        //Using startActivity to go to next Activity
        startActivity(intent);
    }

}
