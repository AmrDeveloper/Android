package com.amrdeveloper.reposearch;

/**
 * Created by AmrDeveloper on 2/10/2018.
 */

public class Repository {

    private String name;
    private String owner;
    private String language;
    private String starsCount;

    private final String nameStart = "Name : ";
    private final String ownerStart = "Owner : ";
    private final String languageStart = "Langauge : ";
    private final String starStart = "Stars Number : ";

    public Repository(String name, String owner, String language, String starsCount) {
        this.name = name;
        this.owner = owner;
        this.language = language;
        this.starsCount = starsCount;
    }

    public String getName() {
        return this.nameStart + name;
    }

    public String getOwner() {
        return this.ownerStart + owner;
    }

    public String getLanguage() {
        return this.languageStart + language;
    }

    public String getStarsCount() {
        return this.starStart + starsCount;
    }
}
