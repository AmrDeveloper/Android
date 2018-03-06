package com.worldnews.amrdeveloper.worldnews.Model;

import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AmrDeveloper on 1/21/2018.
 */

//Model Class For Every news In API
public class News {

    //News Title
    private String title;
    //News Date
    private String date;
    //News Pillar Name
    private String Pillar;
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
        this.Pillar = pillar;
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
        return Pillar;
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

}
