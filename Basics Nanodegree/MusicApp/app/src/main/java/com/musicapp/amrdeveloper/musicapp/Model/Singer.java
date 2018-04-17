package com.musicapp.amrdeveloper.musicapp.Model;

/**
 * Created by AmrDeveloper on 4/3/2018.
 */

public class Singer {

    /**
     * The Singer Name
     */
    private String mSingerName;
    /**
     * The Singer Image Resource Id
     */
    private int mSingerImageId = NO_IMAGE_PROVIDED;
    /**
     * int to check if this singer has image or not
     */
    private static final int NO_IMAGE_PROVIDED = -1;

    public Singer(String mSingerName, int mSingerImageId) {
        this.mSingerName = mSingerName;
        this.mSingerImageId = mSingerImageId;
    }

    /**
     * @return : Get The Singer name
     */
    public String getSingerName() {
        return mSingerName;
    }

    /**
     * @return : The Singer Image Drawable id
     */
    public int getSingerImageId() {
        return mSingerImageId;
    }

    /**
     * @return : Check if this singer has image of not
     */
    public boolean singerHasImage(){
        return (this.mSingerImageId != NO_IMAGE_PROVIDED);
    }
}
