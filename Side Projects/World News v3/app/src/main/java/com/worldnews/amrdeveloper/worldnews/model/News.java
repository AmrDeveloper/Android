package com.worldnews.amrdeveloper.worldnews.model;

import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;

/**
 * Created by AmrDeveloper on 1/21/2018.
 */

//Model Class For Every news In Api
public class News {

    //News Title
    private String title;
    //News Date
    private String date;
    //News Pillar Name
    private String pillar;
    //The Full News Url
    private String newsUrl;
    //News Image Url
    private Bitmap imageUrl;
    //Text Trail
    private String trailText;
    //News Author
    private String author;

    public News(String title, String date, String pillar, String trailText, String newsUrl, Bitmap imageUrl , String author) {
        this.title = title;
        this.date = date;
        this.pillar = pillar;
        this.trailText = trailText;
        this.newsUrl = newsUrl;
        this.imageUrl = imageUrl;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPillar() {
        return pillar;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getShortDescription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(trailText, Html.FROM_HTML_MODE_LEGACY).toString();
        else
            return Html.fromHtml(trailText).toString();
    }

    public Bitmap getImageBitmap() {
        return imageUrl;
    }

    public String getNewsAuthor(){
        return this.author;
    }

    @Override
    public String toString() {
        return title;
    }
}
