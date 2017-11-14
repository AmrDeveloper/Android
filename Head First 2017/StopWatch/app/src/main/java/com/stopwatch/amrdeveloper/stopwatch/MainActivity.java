package com.stopwatch.amrdeveloper.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //to count how many seconds and start count from zero
    private int seconds = 0;
    //to check if StopWatch currently running
    private boolean running;
    //check if stopwatch was running
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get value from bundle when activity creation
        //if this is first time the bundle object will equal null
        //if this object not equal null get value of seconds and running
        if(savedInstanceState != null){
            this.seconds = savedInstanceState.getInt("seconds");
            this.running = savedInstanceState.getBoolean("running");
            this.wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        //run when MainActivity get created
        runTimer();
    }
    
    @Override
    public void onPause(){
        super.onPause();
        //make was running equal current state
        this.wasRunning = this.running;
        //then stop running because this activity not appears in the foreground
        this.running = false;
    }

    @Override
    public void onResume(){
        super.onResume();
        //if stopwatch State was running before on Pause
        //make it running now to count
        if(this.wasRunning){
            this.running = true;
        }
    }
    
    //Override OnSaveInstanceState To save seconds and running
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        //put seconds in bundle
        savedInstanceState.putInt("seconds",seconds);
        //put running to bundle
        savedInstanceState.putBoolean("running",running);
        //put wasRunning to bundle
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    //On Click for Start Button
    public void onClickStart(View view) {
        //when user click on start button running will be true
        this.running = true;
    }
    //On Click for Stop Button
    public void onClickStop(View view) {
        //when user click on stop button running will be false
        this.running = false;
    }
    //On Click for Reset Button
    public void onClickReset(View view) {
        //when user click on reset button running will be false
        this.running = false;
        //and seconds will be zero
        this.seconds = 0;
    }

    //this method will
    //1 - format seconds into hours , minutes and seconds
    //2 - update the TextView
    private void runTimer(){
        final TextView time_view = (TextView)findViewById(R.id.time_view);
        //Make Handler to check every one second
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //format seconds
                //hours = second / (60 * 60)
                int hours = seconds / 3600 ;
                //after take number of hours convert other second to int number of minutes
                int minutes = (seconds % 3600) / 60;
                //after take hours and minutes the other will be second
                int secs = seconds % 60;
                //format time
                String time = String.format("%d:%02d:%02d",hours,minutes,secs);
                //update timeview
                time_view.setText(time);
                //if running is true
                if(running){
                    seconds++;
                }
                //run every one second
                handler.postDelayed(this,1000);
            }
        });
    }

}
