package com.amrdeveloper.tourguide;

/**
 * Created by AmrDeveloper on 2/24/2018.
 */

public class Monument {

    //Monument Name
    private String name;
    //Monument Description
    private String description;
    //Monument Image Resource Id
    private int imageResourceId;

    //Constructor
    public Monument(String name , String description , int imageResourceId){
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    //Getter Methods
    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }
}
