package com.educational.amrdeveloper.educationalapp;

import java.util.Random;

public class QuestionCreator {

    private int level;
    private int numberOne;
    private int numberTwo;
    private int trueReseult;
    private Random random;
    private char operation;
    //List of Math Operation
    private final char[] operationList = {'+','-','*','/'};
    //List of Math operation as String
    private final String[] operationListStr = {"Addition","Subtraction","Division","Multiplication"};


    //Constructor
    public QuestionCreator(){
        this.random = new Random();
    }

    //Get Randmoly Math Operation
    private void getOperation(){
        int index = random.nextInt(3);
        this.operation = this.operationList[index];
    }

    //Generate Number to use it
    private int generateNumber(int low){
        return (random.nextInt(level * 5) + low);
    }

    //Generate Two Number
    private void generateTwoNumber(){
        //Number Two from one to level
        this.numberTwo = generateNumber(level);
        //Make Sure Number one from number two to level + number two
        this.numberOne = generateNumber(this.numberTwo);
        int r = this.numberOne % this.numberTwo;
        if(r != 0){
            this.numberOne += r;
        }
    }

    //Return The True Result
    private void getTrueResult(){
        switch(operation){
            case '+':
                trueReseult = (this.numberOne + this.numberTwo);
                break;
            case '-':
                trueReseult = (this.numberOne - this.numberTwo);
                break;
            case '*':
                trueReseult = (this.numberOne * this.numberTwo);
                break;
            case '/':
                trueReseult = (this.numberOne / this.numberTwo);
                break;
        }
    }

    //Generate Randomly Result
    private int getRandomResult(){
        int result = 0;
        switch(operation){
            case '+':
                result = random.nextInt(this.numberOne * 2) + this.numberTwo;
                break;
            case '-':
                result = random.nextInt(this.numberOne);
                break;
            case '*':
                result = random.nextInt(this.numberOne * this.numberTwo) + this.numberOne;
                break;
            case '/':
                result = random.nextInt(this.numberOne) + 1;
                break;
        }
        return result;
    }

    //Make Full Question Body
    private String makeQuestionBody(){
        String fullQuestion = "";
        switch(operation){
            case '+':
                fullQuestion = operationListStr[0] + " " + this.numberOne + " and " + this.numberTwo;
                break;

            case '-':
                fullQuestion = operationListStr[1] + " " + this.numberOne + " and " + this.numberTwo;
                break;

            case '/':
                fullQuestion = operationListStr[2] + " " + this.numberOne + " by " + this.numberTwo;
                break;

            case '*':
                fullQuestion = operationListStr[3] + " " + this.numberOne + " by " + this.numberTwo;
                break;

        }
        return fullQuestion;
    }

    //Return This Question
    public Question CreateQuestion(int level){
        this.level = level;
        //Create Operation
        getOperation();
        //Create Two Number
        generateTwoNumber();
        //Get True Result
        getTrueResult();
        //Get Three Result
        int result1 = getRandomResult();
        //Make Every Result Different
        while(result1 == trueReseult){
            result1 = ++result1;
        }
        int result2 = getRandomResult();
        while((result2 == result1) || (result2 == trueReseult)){
            result2 = ++result2;
        }
        int result3 = getRandomResult();
        while((result3 == result1) || (result3 == result2) || (result3 == trueReseult)){
            result3 = ++result3;
        }
        //Create Question Body
        String questionStr = makeQuestionBody();
        //Create Question Object and return it
        Question question = new Question(questionStr,
                String.valueOf(this.trueReseult)
                ,String.valueOf(result1)
                ,String.valueOf(result2),
                String.valueOf(result3));
        return question;
    }
}
