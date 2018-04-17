package com.musicapp.amrdeveloper.musicapp.Model;

/**
 * Created by AmrDeveloper on 4/3/2018.
 */

public class Song {

    /**
     * The Song Name
     */
    private String mSongName;

    private String mSingerName;

    /**
     * The Song Audio Id In Resource
     */
    private int mSongResourceId;


    public Song(String mSongName,String mSingerName, int mSongResourceId) {
        this.mSongName = mSongName;
        this.mSingerName = mSingerName;
        this.mSongResourceId = mSongResourceId;

    }

    /**
     * @return : Return Song Name
     */
    public String getSongName() {
        return mSongName;
    }

    public String getSingerName(){
        return mSingerName;
    }

    /**
     * @return : get The Song Audio Resource Id
     */
    public int getSongResourceId() {
        return mSongResourceId;
    }
}
