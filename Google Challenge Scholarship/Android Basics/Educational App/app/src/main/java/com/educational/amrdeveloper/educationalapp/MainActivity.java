package com.educational.amrdeveloper.educationalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //User Level Start From Zero
    private int level = 1;
    //Tags
    private final String LEVEL_TAG = "level";
    //TextView For Every Question
    private TextView question1;
    private TextView question2;
    private TextView question3;
    private TextView question4;
    private TextView question5;
    private TextView userLevel;
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
    //Full Score
    private int userScore = 0;
    //boolean for check if this first time on app or not
    private boolean isFirst = true;

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
            this.question1.setText(savedInstanceState.getString("question1"));
            this.question2.setText(savedInstanceState.getString("question2"));
            this.question3.setText(savedInstanceState.getString("question3"));
            this.question4.setText(savedInstanceState.getString("question4"));
            this.question5.setText(savedInstanceState.getString("question5"));
            //Set Results
            //Question1
            this.result1_1.setText(savedInstanceState.getString("result1_1"));
            this.result1_2.setText(savedInstanceState.getString("result1_2"));
            this.result1_3.setText(savedInstanceState.getString("result1_3"));
            this.result1_4.setText(savedInstanceState.getString("result1_4"));
            //Question2
            this.result2_1.setText(savedInstanceState.getString("result2_1"));
            this.result2_2.setText(savedInstanceState.getString("result2_2"));
            this.result2_3.setText(savedInstanceState.getString("result2_3"));
            this.result2_4.setText(savedInstanceState.getString("result2_4"));
            //Question3
            this.result3_1.setText(savedInstanceState.getString("result3_1"));
            this.result3_2.setText(savedInstanceState.getString("result3_2"));
            this.result3_3.setText(savedInstanceState.getString("result3_3"));
            this.result3_4.setText(savedInstanceState.getString("result3_4"));
            //Question4
            this.result4_1.setText(savedInstanceState.getString("result4_1"));
            this.result4_2.setText(savedInstanceState.getString("result4_2"));
            this.result4_3.setText(savedInstanceState.getString("result4_3"));
            this.result4_4.setText(savedInstanceState.getString("result4_4"));
            //Question5
            this.result5_1.setText(savedInstanceState.getString("result5_1"));
            this.result5_2.setText(savedInstanceState.getString("result5_2"));
            this.result5_3.setText(savedInstanceState.getString("result5_3"));
            this.result5_4.setText(savedInstanceState.getString("result5_4"));
            //Set True Result
            this.question_true_Result1 = savedInstanceState.getString("true_result1");
            this.question_true_Result2 = savedInstanceState.getString("true_result2");
            this.question_true_Result3 = savedInstanceState.getString("true_result3");
            this.question_true_Result4 = savedInstanceState.getString("true_result4");
            this.question_true_Result5 = savedInstanceState.getString("true_result5");
            //Set Current User Result
            this.question_user_Result1 = savedInstanceState.getString("user_result1");
            this.question_user_Result2 = savedInstanceState.getString("user_result2");
            this.question_user_Result3 = savedInstanceState.getString("user_result3");
            this.question_user_Result4 = savedInstanceState.getString("user_result4");
            this.question_user_Result5 = savedInstanceState.getString("user_result5");
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

    //Un Check All Radio Button
    private void unCheckAllRadioButtons(){
        result1_1.setChecked(false);
        result1_2.setChecked(false);
        result1_3.setChecked(false);
        result1_4.setChecked(false);
        result2_1.setChecked(false);
        result2_2.setChecked(false);
        result2_3.setChecked(false);
        result2_4.setChecked(false);
        result3_1.setChecked(false);
        result3_2.setChecked(false);
        result3_3.setChecked(false);
        result3_4.setChecked(false);
        result4_1.setChecked(false);
        result4_2.setChecked(false);
        result4_3.setChecked(false);
        result4_4.setChecked(false);
        result5_1.setChecked(false);
        result5_2.setChecked(false);
        result5_3.setChecked(false);
        result5_4.setChecked(false);
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

    //Submit The Result And Show Score and if score is full go next round
    public void sumbitResultButton(View v) {
        //Submit Result
        setUserScore();
        //If Score Is Full Go Next Level
        if (this.userScore == 5) {
            setQuestion1();
            setQuestion2();
            setQuestion3();
            setQuestion4();
            setQuestion5();
            level++;
            userLevel.setText("Level : " + level);
            this.userScore = 0;
            unCheckAllRadioButtons();
            Toast.makeText(this, "Full Score \n Go to Next Level now : D", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Sorry You Need Full Score to Go Next Level\n ~_~", Toast.LENGTH_SHORT).show();
        }
    }

    //Get Qustion 1 result
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

    //Get Qustion 2 result
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

    //Get Qustion 3 result
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

    //Get Qustion 4 result
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

    //Get Qustion 5 result
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
        outState.putString("question1",question1.getText().toString());
        outState.putString("question2",question2.getText().toString());
        outState.putString("question3",question3.getText().toString());
        outState.putString("question4",question4.getText().toString());
        outState.putString("question5",question5.getText().toString());
        //Save Result For Every Question
        //Question1
        outState.putString("result1_1",result1_1.getText().toString());
        outState.putString("result1_2",result1_2.getText().toString());
        outState.putString("result1_3",result1_3.getText().toString());
        outState.putString("result1_4",result1_4.getText().toString());
        //Question2
        outState.putString("result2_1",result2_1.getText().toString());
        outState.putString("result2_2",result2_2.getText().toString());
        outState.putString("result2_3",result2_3.getText().toString());
        outState.putString("result2_4",result2_4.getText().toString());
        //Question3
        outState.putString("result3_1",result3_1.getText().toString());
        outState.putString("result3_2",result3_2.getText().toString());
        outState.putString("result3_3",result3_3.getText().toString());
        outState.putString("result3_4",result3_4.getText().toString());
        //Question4
        outState.putString("result4_1",result4_1.getText().toString());
        outState.putString("result4_2",result4_2.getText().toString());
        outState.putString("result4_3",result4_3.getText().toString());
        outState.putString("result4_4",result4_4.getText().toString());
        //Question4
        outState.putString("result5_1",result5_1.getText().toString());
        outState.putString("result5_2",result5_2.getText().toString());
        outState.putString("result5_3",result5_3.getText().toString());
        outState.putString("result5_4",result5_4.getText().toString());
        //Save True Result
        outState.putString("true_result1",question_true_Result1);
        outState.putString("true_result2",question_true_Result2);
        outState.putString("true_result3",question_true_Result3);
        outState.putString("true_result4",question_true_Result4);
        outState.putString("true_result5",question_true_Result5);
        //Set User Current Result
        outState.putString("user_result1",question_user_Result1);
        outState.putString("user_result2",question_user_Result2);
        outState.putString("user_result3",question_user_Result3);
        outState.putString("user_result4",question_user_Result4);
        outState.putString("user_result5",question_user_Result5);
    }
}
