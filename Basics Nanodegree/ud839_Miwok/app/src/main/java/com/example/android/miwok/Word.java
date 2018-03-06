package com.example.android.miwok;

//Class for every words in two language
public class Word {

    private String default_word;
    private String miwok_word;
    //no Image State
    private final int NO_IMAGE_PROVIDED = -1;
    //Start with no image
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    //Miwok Word Audio id
    private int mAudioResourceId;


    //Constructor
    public Word(String default_word , String miwok_word , int mImageResourceId , int mAudioResourceId){
        this.default_word = default_word;
        this.miwok_word = miwok_word;
        this.mImageResourceId = mImageResourceId;
        this.mAudioResourceId = mAudioResourceId;
    }

    //Second Constructor
    public Word(String default_word , String miwok_word , int mAudioResourceId){
        this.default_word = default_word;
        this.miwok_word = miwok_word;
        this.mAudioResourceId = mAudioResourceId;
    }

    //get default Word
    public String getDefaultWord(){
        return this.default_word;
    }

    //get Miwok Word
    public String getMiwokWord(){
        return this.miwok_word;
    }

    //get Image Resource id
    public int getImageResourceId(){
        return this.mImageResourceId;
    }

    //get Audio Resource Id
    public int getAudioResourceId(){
        return this.mAudioResourceId;
    }

    //Check if mImageResourceId not default value
    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    //Override toString method
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + default_word + '\'' +
                ", mMiwokTranslation='" + miwok_word + '\'' +
                ", mAudioResourceId=" + mAudioResourceId +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }
}
