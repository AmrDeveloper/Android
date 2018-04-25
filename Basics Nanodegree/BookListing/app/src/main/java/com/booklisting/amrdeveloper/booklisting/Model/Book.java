package com.booklisting.amrdeveloper.booklisting.Model;

public class Book {

    //Book Model Class
    //Book Title
    private String bookTitle;
    //Book Image Url
    private String bookImageUrl;
    //Book Publisher
    private String bookPublisher;
    //Book Categories
    private String bookCategories;
    //Book Description
    private String bookDescription;

    //Constructor
    public Book(String bookTitle, String bookImageUrl, String bookPublisher, String bookCategories, String bookDescription) {
        this.bookTitle = bookTitle;
        this.bookImageUrl = bookImageUrl;
        this.bookPublisher = bookPublisher;
        this.bookCategories = bookCategories;
        this.bookDescription = bookDescription;
    }

    //Getter
    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public String getBookCategories() {
        return bookCategories;
    }

    public String getBookDescription() {
        return bookDescription;
    }
}
