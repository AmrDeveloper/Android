package com.worldnews.amrdeveloper.worldnews.Model;

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

    public News(String title, String date, String pillar ,String newsUrl ,String imageUrl) {
        this.title = title;
        this.date = date;
        this.Pillar = pillar;
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

}
