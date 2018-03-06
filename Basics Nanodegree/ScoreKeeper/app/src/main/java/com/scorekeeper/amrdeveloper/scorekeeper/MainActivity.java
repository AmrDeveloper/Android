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

    private final String POINT = " point";
    private final String POINTS = " points";
    private final String TEAM_A = "teamA";
    private final String TEAM_B = "teamB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializes TextViews for Team one and two
        initTextViews();

        //Check if savedInstanceState equal null or not
        if(savedInstanceState != null){
            //get The last Score
            this.teamAScore = savedInstanceState.getInt(TEAM_A,0);
            this.teamBScore = savedInstanceState.getInt(TEAM_B,0);
            //Update Ui
            setScoreInTextView(scoreTeamA,teamAScore);
            setScoreInTextView(scoreTeamB,teamBScore);
        }
    }

    //Initializes TextViews
    private void initTextViews(){
        this.scoreTeamA =  findViewById(R.id.scoreTeamA);
        this.scoreTeamB =  findViewById(R.id.scoreTeamB);
    }

    //Team A Score Add One
    public void teamOnePlusOne(View v) {
        //First update Score
        this.teamAScore = teamAScore + 1;
        //Update Ui
        setScoreInTextView(scoreTeamA,teamAScore);
    }

    //Team A Score Add Two
    public void teamOnePlusTwo(View v) {
        //First update Score
        this.teamAScore = teamAScore + 2;
        //Update Ui
        setScoreInTextView(scoreTeamA,teamAScore);
    }

    //Team A Score Add Three
    public void teamOnePlusThree(View v) {
        //First update Score
        this.teamAScore = teamAScore + 3;
        //Update Ui
        setScoreInTextView(scoreTeamA,teamAScore);
    }

    //Team B Score Add Two
    public void teamTwoPlusOne(View v) {
        //First update Score
        this.teamBScore = teamBScore + 1;
        //Update Ui
        setScoreInTextView(scoreTeamB,teamBScore);
    }

    // Team B Score Add Two
    public void teamTwoPlusTwo(View v) {
        //First update Score
        this.teamBScore = teamBScore + 2;
        //Update Ui
        setScoreInTextView(scoreTeamB,teamBScore);
    }

    //Team B Score Add Three
    public void teamTwoPlusThree(View v) {
        //First update Score
        this.teamBScore = teamBScore + 3;
        //Update Ui
        setScoreInTextView(scoreTeamB,teamBScore);
    }

    //Reset Score For Team A And B
    public void scoreReset(View v) {
        //Reset Two Team Score to zero
        this.teamAScore = 0;
        this.teamBScore = 0;
        //Update Ui
        setScoreInTextView(scoreTeamA,teamAScore);
        setScoreInTextView(scoreTeamB,teamBScore);
    }

    //Set Score in TextView and check if score is one or more
    private void setScoreInTextView(TextView text ,int score){
        if(score == 1){
           text.setText(score + POINT);
        }
        else{
            text.setText(score + POINTS);
        }
    }

    @Override
    //Save Current Score Before Activity Destroyed
    protected void onSaveInstanceState(Bundle outState) {
        //Put Current Team A Score on Bundle
        outState.putInt(TEAM_A,teamAScore);
        //Put Current Team B Score on Bundle
        outState.putInt(TEAM_B,teamBScore);
        super.onSaveInstanceState(outState);
    }

}
