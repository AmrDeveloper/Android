package com.educational.amrdeveloper.educationalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //User Level Start From Zero
    private int level = 1;
    //User Score
    private int userScore = 0;
    //Tags
    private final String LEVEL_TAG = "level";
    //TextView For Every Question
    private TextView question1;
    private TextView question2;
    private TextView question3;
    private TextView question4;
    private TextView question5;
    private TextView userLevel;
    //RadioGroup For Every Question
    private RadioGroup resultGroup1;
    private RadioGroup resultGroup2;
    private RadioGroup resultGroup3;
    private RadioGroup resultGroup4;
    private RadioGroup resultGroup5;
    //25 CheckBox
    //Check box for first Question
    private RadioButton result1_1;
    private RadioButton result1_2;
    private RadioButton result1_3;
    private RadioButton result1_4;
    //Check box for Question 1
    private RadioButton result2_1;
    private RadioButton result2_2;
    private RadioButton result2_3;
    private RadioButton result2_4;
    //Check box for Question 3
    private RadioButton result3_1;
    private RadioButton result3_2;
    private RadioButton result3_3;
    private RadioButton result3_4;
    //Check box for Question $
    private RadioButton result4_1;
    private RadioButton result4_2;
    private RadioButton result4_3;
    private RadioButton result4_4;
    //Check box for Question 5
    private RadioButton result5_1;
    private RadioButton result5_2;
    private RadioButton result5_3;
    private RadioButton result5_4;
    //Question Creator Object
    private QuestionCreator questionCreator;
    //User Result For Every Question
    private String question_user_Result1 = "";
    private String question_user_Result2 = "";
    private String question_user_Result3 = "";
    private String question_user_Result4 = "";
    private String question_user_Result5 = "";
    //The True Result For Every Question
    private String question_true_Result1;
    private String question_true_Result2;
    private String question_true_Result3;
    private String question_true_Result4;
    private String question_true_Result5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Question Creator Initialization
        questionCreator = new QuestionCreator();
        //Level TextView
        userLevel = (TextView) findViewById(R.id.userLevel);
        //Initialization Text Views
        initializationTextViews();
        //Initialization Radio Buttons
        initializationRadioButtons();

        //Get Current level
        if (savedInstanceState != null)
        {
            this.level = savedInstanceState.getInt(LEVEL_TAG, 0);
            //Update Level TextView
            userLevel.setText("Level : " + level);
            //Get The 5 Questions
            this.question1.setText(savedInstanceState.getString(QuestionTags.QUESTION1));
            this.question2.setText(savedInstanceState.getString(QuestionTags.QUESTION2));
            this.question3.setText(savedInstanceState.getString(QuestionTags.QUESTION3));
            this.question4.setText(savedInstanceState.getString(QuestionTags.QUESTION4));
            this.question5.setText(savedInstanceState.getString(QuestionTags.QUESTION5));
            //Set Results
            //Question1
            this.result1_1.setText(savedInstanceState.getString(QuestionTags.RESULT1_1));
            this.result1_2.setText(savedInstanceState.getString(QuestionTags.RESULT1_2));
            this.result1_3.setText(savedInstanceState.getString(QuestionTags.RESULT1_3));
            this.result1_4.setText(savedInstanceState.getString(QuestionTags.RESULT1_4));
            //Question2
            this.result2_1.setText(savedInstanceState.getString(QuestionTags.RESULT2_1));
            this.result2_2.setText(savedInstanceState.getString(QuestionTags.RESULT2_2));
            this.result2_3.setText(savedInstanceState.getString(QuestionTags.RESULT2_3));
            this.result2_4.setText(savedInstanceState.getString(QuestionTags.RESULT2_4));
            //Question3
            this.result3_1.setText(savedInstanceState.getString(QuestionTags.RESULT3_1));
            this.result3_2.setText(savedInstanceState.getString(QuestionTags.RESULT3_2));
            this.result3_3.setText(savedInstanceState.getString(QuestionTags.RESULT3_3));
            this.result3_4.setText(savedInstanceState.getString(QuestionTags.RESULT3_4));
            //Question4
            this.result4_1.setText(savedInstanceState.getString(QuestionTags.RESULT4_1));
            this.result4_2.setText(savedInstanceState.getString(QuestionTags.RESULT4_2));
            this.result4_3.setText(savedInstanceState.getString(QuestionTags.RESULT4_3));
            this.result4_4.setText(savedInstanceState.getString(QuestionTags.RESULT4_4));
            //Question5
            this.result5_1.setText(savedInstanceState.getString(QuestionTags.RESULT5_1));
            this.result5_2.setText(savedInstanceState.getString(QuestionTags.RESULT5_2));
            this.result5_3.setText(savedInstanceState.getString(QuestionTags.RESULT5_3));
            this.result5_4.setText(savedInstanceState.getString(QuestionTags.RESULT5_4));
            //Set True Result
            this.question_true_Result1 = savedInstanceState.getString(QuestionTags.TRUE_RESULT1);
            this.question_true_Result2 = savedInstanceState.getString(QuestionTags.TRUE_RESULT2);
            this.question_true_Result3 = savedInstanceState.getString(QuestionTags.TRUE_RESULT3);
            this.question_true_Result4 = savedInstanceState.getString(QuestionTags.TRUE_RESULT4);
            this.question_true_Result5 = savedInstanceState.getString(QuestionTags.TRUE_RESULT5);
            //Set Current User Result
            this.question_user_Result1 = savedInstanceState.getString(QuestionTags.USER_RESULT1);
            this.question_user_Result2 = savedInstanceState.getString(QuestionTags.USER_RESULT2);
            this.question_user_Result3 = savedInstanceState.getString(QuestionTags.USER_RESULT3);
            this.question_user_Result4 = savedInstanceState.getString(QuestionTags.USER_RESULT4);
            this.question_user_Result5 = savedInstanceState.getString(QuestionTags.USER_RESULT5);
        }
        else
        {
            //Set Question
            setQuestion1();
            setQuestion2();
            setQuestion3();
            setQuestion4();
            setQuestion5();
        }
    }

    //Initialization 5 Text Views
    private void initializationTextViews() {
        this.question1 = (TextView) findViewById(R.id.question1);
        this.question2 = (TextView) findViewById(R.id.question2);
        this.question3 = (TextView) findViewById(R.id.question3);
        this.question4 = (TextView) findViewById(R.id.question4);
        this.question5 = (TextView) findViewById(R.id.question5);
    }

    //Initialization 25 Radio Buttons
    private void initializationRadioButtons() {
        //For Question 1
        this.result1_1 = (RadioButton) findViewById(R.id.result1_1);
        this.result1_2 = (RadioButton) findViewById(R.id.result1_2);
        this.result1_3 = (RadioButton) findViewById(R.id.result1_3);
        this.result1_4 = (RadioButton) findViewById(R.id.result1_4);

        //For Question 2
        this.result2_1 = (RadioButton) findViewById(R.id.result2_1);
        this.result2_2 = (RadioButton) findViewById(R.id.result2_2);
        this.result2_3 = (RadioButton) findViewById(R.id.result2_3);
        this.result2_4 = (RadioButton) findViewById(R.id.result2_4);

        //For Question 3
        this.result3_1 = (RadioButton) findViewById(R.id.result3_1);
        this.result3_2 = (RadioButton) findViewById(R.id.result3_2);
        this.result3_3 = (RadioButton) findViewById(R.id.result3_3);
        this.result3_4 = (RadioButton) findViewById(R.id.result3_4);

        //For Question 3
        this.result4_1 = (RadioButton) findViewById(R.id.result4_1);
        this.result4_2 = (RadioButton) findViewById(R.id.result4_2);
        this.result4_3 = (RadioButton) findViewById(R.id.result4_3);
        this.result4_4 = (RadioButton) findViewById(R.id.result4_4);

        //For Question 4
        this.result5_1 = (RadioButton) findViewById(R.id.result5_1);
        this.result5_2 = (RadioButton) findViewById(R.id.result5_2);
        this.result5_3 = (RadioButton) findViewById(R.id.result5_3);
        this.result5_4 = (RadioButton) findViewById(R.id.result5_4);
    }

    //un Check all Radio Buttons Using Radio Groups
    private void unCheckAllRadioGroups(){
        //initialization Five Radio Group one for every Question
        resultGroup1 = (RadioGroup)findViewById(R.id.resultGroup1);
        resultGroup2 = (RadioGroup)findViewById(R.id.resultGroup2);
        resultGroup3 = (RadioGroup)findViewById(R.id.resultGroup3);
        resultGroup4 = (RadioGroup)findViewById(R.id.resultGroup4);
        resultGroup5 = (RadioGroup)findViewById(R.id.resultGroup5);
        //Clear Check for all Gruoup
        resultGroup1.clearCheck();
        resultGroup2.clearCheck();
        resultGroup3.clearCheck();
        resultGroup4.clearCheck();
        resultGroup5.clearCheck();
    }

    //Question 1 set title and results
    private void setQuestion1() {
        Question question = questionCreator.CreateQuestion(level);
        question1.setText(question.getQuestion());
        List<String> result = question.getResultList();
        result1_1.setText(result.get(0));
        result1_2.setText(result.get(1));
        result1_3.setText(result.get(2));
        result1_4.setText(result.get(3));
        //Set True Result
        question_true_Result1 = question.getTrueResult();
    }

    //Question 2 set title and results
    private void setQuestion2() {
        Question question = questionCreator.CreateQuestion(level);
        question2.setText(question.getQuestion());
        List<String> result = question.getResultList();
        result2_1.setText(result.get(0));
        result2_2.setText(result.get(1));
        result2_3.setText(result.get(2));
        result2_4.setText(result.get(3));
        //Set True Result
        question_true_Result2 = question.getTrueResult();
    }

    //Question 3 set title and results
    private void setQuestion3() {
        Question question = questionCreator.CreateQuestion(level);
        question3.setText(question.getQuestion());
        List<String> result = question.getResultList();
        result3_1.setText(result.get(0));
        result3_2.setText(result.get(1));
        result3_3.setText(result.get(2));
        result3_4.setText(result.get(3));
        //Set True Result
        question_true_Result3 = question.getTrueResult();
    }

    //Question 4 set title and results
    private void setQuestion4() {
        Question question = questionCreator.CreateQuestion(level);
        question4.setText(question.getQuestion());
        List<String> result = question.getResultList();
        result4_1.setText(result.get(0));
        result4_2.setText(result.get(1));
        result4_3.setText(result.get(2));
        result4_4.setText(result.get(3));
        //Set True Result
        question_true_Result4 = question.getTrueResult();
    }

    //Question 5 set title and results
    private void setQuestion5() {
        Question question = questionCreator.CreateQuestion(level);
        question5.setText(question.getQuestion());
        List<String> result = question.getResultList();
        result5_1.setText(result.get(0));
        result5_2.setText(result.get(1));
        result5_3.setText(result.get(2));
        result5_4.setText(result.get(3));
        //Set True Result
        question_true_Result5 = question.getTrueResult();
    }

    //Submit Result and Set Score for every question
    private void setUserScore() {
        this.userScore = 0;
        if (question_user_Result1.equals(question_true_Result1))
            userScore = userScore + 1;

        if (question_user_Result2.equals(question_true_Result2))
            userScore = userScore + 1;

        if (question_user_Result3.equals(question_true_Result3))
            userScore = userScore + 1;

        if (question_user_Result4.equals(question_true_Result4))
            userScore = userScore + 1;

        if (question_user_Result5.equals(question_true_Result5))
            userScore = userScore + 1;
    }

    //Submit The Result And Show Score and if score is full go next Level
    public void submitResultButton(View v) {
        //Submit Result
        setUserScore();
        //If Score Is Full Go Next Level
        if (this.userScore == 5) {
            //Change Question To New Questions
            setQuestion1();
            setQuestion2();
            setQuestion3();
            setQuestion4();
            setQuestion5();
            //Go Next Level
            level++;
            userLevel.setText("Level : " + level);
            //Reset User Score to Zero
            this.userScore = 0;
            //un Check All Radio Buttons
            unCheckAllRadioGroups();
            //Print Full Score Message
            Toast.makeText(this, getResources().getString(R.string.full_scaore), Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Print Current Score and Stay in Current Level
            Toast.makeText(this, getResources().getString(R.string.not_full_score) + userScore, Toast.LENGTH_SHORT).show();
        }
    }

    //Get Question 1 user result
    public void onQuestionResult1(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.result1_1:
                if (checked)
                    this.question_user_Result1 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result1_2:
                if (checked)
                    this.question_user_Result1 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result1_3:
                if (checked)
                    this.question_user_Result1 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result1_4:
                if (checked)
                    this.question_user_Result1 = ((RadioButton) view).getText().toString();
                break;
        }
    }

    //Get Question 2 user result
    public void onQuestionResult2(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.result2_1:
                if (checked)
                    this.question_user_Result2 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result2_2:
                if (checked)
                    this.question_user_Result2 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result2_3:
                if (checked)
                    this.question_user_Result2 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result2_4:
                if (checked)
                    this.question_user_Result2 = ((RadioButton) view).getText().toString();
                break;
        }
    }

    //Get Question 3 user result
    public void onQuestionResult3(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.result3_1:
                if (checked)
                    this.question_user_Result3 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result3_2:
                if (checked)
                    this.question_user_Result3 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result3_3:
                if (checked)
                    this.question_user_Result3 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result3_4:
                if (checked)
                    this.question_user_Result3 = ((RadioButton) view).getText().toString();
                break;
        }
    }

    //Get Question 4 user result
    public void onQuestionResult4(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.result4_1:
                if (checked)
                    this.question_user_Result4 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result4_2:
                if (checked)
                    this.question_user_Result4 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result4_3:
                if (checked)
                    this.question_user_Result4 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result4_4:
                if (checked)
                    this.question_user_Result4 = ((RadioButton) view).getText().toString();
                break;
        }
    }

    //Get Question 5 user result
    public void onQuestionResult5(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.result5_1:
                if (checked)
                    this.question_user_Result5 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result5_2:
                if (checked)
                    this.question_user_Result5 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result5_3:
                if (checked)
                    this.question_user_Result5 = ((RadioButton) view).getText().toString();
                break;
            case R.id.result5_4:
                if (checked)
                    this.question_user_Result5 = ((RadioButton) view).getText().toString();
                break;
        }
    }

    //Save Current level
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save Current level
        outState.putInt(LEVEL_TAG, level);
        //Save Every Question
        outState.putString(QuestionTags.QUESTION1 , question1.getText().toString());
        outState.putString(QuestionTags.QUESTION2 , question2.getText().toString());
        outState.putString(QuestionTags.QUESTION3 , question3.getText().toString());
        outState.putString(QuestionTags.QUESTION4 , question4.getText().toString());
        outState.putString(QuestionTags.QUESTION5 , question5.getText().toString());
        //Save Result For Every Question
        //Question1
        outState.putString(QuestionTags.RESULT1_1 , result1_1.getText().toString());
        outState.putString(QuestionTags.RESULT1_2 , result1_2.getText().toString());
        outState.putString(QuestionTags.RESULT1_3 , result1_3.getText().toString());
        outState.putString(QuestionTags.RESULT1_4 ,result1_4.getText().toString());
        //Question2
        outState.putString(QuestionTags.RESULT2_1 , result2_1.getText().toString());
        outState.putString(QuestionTags.RESULT2_2 , result2_2.getText().toString());
        outState.putString(QuestionTags.RESULT2_3 , result2_3.getText().toString());
        outState.putString(QuestionTags.RESULT2_4 , result2_4.getText().toString());
        //Question3
        outState.putString(QuestionTags.RESULT3_1 , result3_1.getText().toString());
        outState.putString(QuestionTags.RESULT3_2 , result3_2.getText().toString());
        outState.putString(QuestionTags.RESULT3_3 , result3_3.getText().toString());
        outState.putString(QuestionTags.RESULT3_4 , result3_4.getText().toString());
        //Question4
        outState.putString(QuestionTags.RESULT4_1 , result4_1.getText().toString());
        outState.putString(QuestionTags.RESULT4_2 , result4_2.getText().toString());
        outState.putString(QuestionTags.RESULT4_3 , result4_3.getText().toString());
        outState.putString(QuestionTags.RESULT4_4 , result4_4.getText().toString());
        //Question5
        outState.putString(QuestionTags.RESULT5_1 , result5_1.getText().toString());
        outState.putString(QuestionTags.RESULT5_2 , result5_2.getText().toString());
        outState.putString(QuestionTags.RESULT5_3 , result5_3.getText().toString());
        outState.putString(QuestionTags.RESULT5_4 , result5_4.getText().toString());
        //Save True Result
        outState.putString(QuestionTags.TRUE_RESULT1 , question_true_Result1);
        outState.putString(QuestionTags.TRUE_RESULT2 , question_true_Result2);
        outState.putString(QuestionTags.TRUE_RESULT3 , question_true_Result3);
        outState.putString(QuestionTags.TRUE_RESULT4 , question_true_Result4);
        outState.putString(QuestionTags.TRUE_RESULT5 , question_true_Result5);
        //Set User Current Result
        outState.putString(QuestionTags.USER_RESULT1 , question_user_Result1);
        outState.putString(QuestionTags.USER_RESULT2 , question_user_Result2);
        outState.putString(QuestionTags.USER_RESULT3 , question_user_Result3);
        outState.putString(QuestionTags.USER_RESULT4 , question_user_Result4);
        outState.putString(QuestionTags.USER_RESULT5 , question_user_Result5);
    }
}
