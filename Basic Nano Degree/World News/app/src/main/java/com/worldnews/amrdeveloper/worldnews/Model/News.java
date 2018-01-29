package com.worldnews.amrdeveloper.worldnews.Model;

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
    private String imageUrl;
    //Text Trail
    private String trailText;

    public News(String title, String date, String pillar, String trailText, String newsUrl, String imageUrl) {
        this.title = title;
        this.date = date;
        this.Pillar = pillar;
        this.trailText = trailText;
        this.newsUrl = newsUrl;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getShortDescription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(trailText, Html.FROM_HTML_MODE_LEGACY).toString();
        else
            return Html.fromHtml(trailText).toString();
    }


}
