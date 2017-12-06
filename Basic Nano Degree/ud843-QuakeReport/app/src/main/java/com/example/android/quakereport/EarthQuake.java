package com.example.android.quakereport;

/**
 * Created by AmrDeveloper on 11/19/2017.
 */
//Model Class for Every EarthQuake Report
public class EarthQuake {

    //To Save EarthQuake Mag
    private double mMagnitude;
    //To Save EarthQuake Time
    private long quakeTime;
    //To Save EarthQuake Place
    private String quakePlace;
    //To Save Description Site
    private String mDescriptionSite;

    //Model Class Constructor
    EarthQuake(Double mMagnitude , String quakePlace  , long quakeTime , String mDescriptionSite){
        this.mMagnitude = mMagnitude;
        this.quakeTime = quakeTime;
        this.quakePlace = quakePlace;
        this.mDescriptionSite = mDescriptionSite;
    }

    //Setter for mag
    public void setMagnitude(double quakeMag){
        this.mMagnitude = quakeMag;
    }
    //Setter for Time
    public void setQuakeTime(long quakeTime){
        this.quakeTime = quakeTime;
    }
    //Setter for Place
    public void setQuakePlace(String quakePlace){
        this.quakePlace = quakePlace;
    }
    //Setter For Description Site;
    public void setDescriptionSite(String url){this.mDescriptionSite = url;}

    /**
     * Returns the magnitude of the earthquake.
     */
    public double getMagnitude() {
        return mMagnitude;
    }

    //Getter For time
    public long getQuakeTime() {
        return quakeTime;
    }

    //Getter for place
    public String getQuakePlace() {
        return quakePlace;
    }

    //Getter For Description site
    public String getDescriptionSite(){
        return this.mDescriptionSite;
    }


}
