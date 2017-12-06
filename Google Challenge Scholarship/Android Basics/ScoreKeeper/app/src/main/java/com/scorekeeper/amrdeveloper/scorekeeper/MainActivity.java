package com.scorekeeper.amrdeveloper.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //All Score For Team A Start From Zero
    private int teamAScore = 0;
    //All Score For Team B Start From Zero
    private int teamBScore = 0;

    //TextView For Team A Score
    private TextView scoreTeamA;
    //TextView For Team B Score
    private TextView scoreTeamB;

    private String scoreFotter = " points";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO : Initializes TextViews for Team one and two
        this.scoreTeamA = (TextView) findViewById(R.id.scoreTeamA);
        this.scoreTeamB = (TextView) findViewById(R.id.scoreTeamB);

        //Check if savedInstanceState equal null or not
        if(savedInstanceState != null){
            //get The last Score
            this.teamAScore = savedInstanceState.getInt("teamA",0);
            this.teamBScore = savedInstanceState.getInt("teamB",0);
            //Update Ui
            this.scoreTeamA.setText(teamAScore + scoreFotter);
            this.scoreTeamB.setText(teamBScore + scoreFotter);
        }
    }

    // TODO : Team A Score Add One
    public void teamOnePlusOne(View v) {
        //First update Score
        this.teamAScore = teamAScore + 1;
        //Update Ui
        scoreTeamA.setText(teamAScore + scoreFotter);
    }

    // TODO : Team A Score Add Two
    public void teamOnePlusTwo(View v) {
        //First update Score
        this.teamAScore = teamAScore + 2;
        //Update Ui
        scoreTeamA.setText(teamAScore + scoreFotter);
    }

    // TODO : Team A Score Add Three
    public void teamOnePlusThree(View v) {
        //First update Score
        this.teamAScore = teamAScore + 3;
        //Update Ui
        scoreTeamA.setText(teamAScore + scoreFotter);
    }

    // TODO : Team B Score Add Two
    public void teamTwoPlusOne(View v) {
        //First update Score
        this.teamBScore = teamBScore + 1;
        //Update Ui
        scoreTeamB.setText(teamBScore + scoreFotter);
    }

    // TODO : Team B Score Add Two
    public void teamTwoPlusTwo(View v) {
        //First update Score
        this.teamBScore = teamBScore + 2;
        //Update Ui
        scoreTeamB.setText(teamBScore + scoreFotter);
    }

    // TODO : Team B Score Add Three
    public void teamTwoPlusThree(View v) {
        //First update Score
        this.teamBScore = teamBScore + 3;
        //Update Ui
        scoreTeamB.setText(teamBScore + scoreFotter);
    }

    //TODO : Reset Score For Team A And B
    public void scoreReset(View v) {
        //Reset Two Team Score to zero
        this.teamAScore = 0;
        this.teamBScore = 0;
        //Update Ui
        scoreTeamA.setText(teamAScore + scoreFotter);
        scoreTeamB.setText(teamBScore + scoreFotter);
    }

    @Override
    //TODO : Save Current Score Before Activity Destroyed
    protected void onSaveInstanceState(Bundle outState) {
        //Put Current Team A Score on Bundle
        outState.putInt("teamA",this.teamAScore);
        //Put Current Team B Score on Bundle
        outState.putInt("teamB",this.teamBScore);
        super.onSaveInstanceState(outState);
    }

}
