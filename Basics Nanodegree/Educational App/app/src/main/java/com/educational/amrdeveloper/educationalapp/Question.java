package com.educational.amrdeveloper.educationalapp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author AmrDeveloper
 */
public class Question {

    private String trueResult;
    private String resultOne;
    private String resultTwo;
    private String resultThree;
    private String questionBody;
    private List<String> resultList;

    //Constructor
    public Question(String questionBody,
                    String trueResult,
                    String resultOne,
                    String resultTwo,
                    String resultThree) {
        this.questionBody = questionBody;
        this.trueResult = trueResult;
        this.resultOne = resultOne;
        this.resultTwo = resultTwo;
        this.resultThree = resultThree;
        resultList = new ArrayList();
    }

    public String getQuestion() {
        return this.questionBody;
    }

    public String getTrueResult(){
        return this.trueResult;
    }

    public List<String> getResultList() {
        //Add Four Result on The List
        resultList.add(trueResult);
        resultList.add(resultOne);
        resultList.add(resultTwo);
        resultList.add(resultThree);
        //Shuffle The Result List
        Collections.shuffle(resultList);
        //Return Array Of Result
        return resultList;
    }

}
