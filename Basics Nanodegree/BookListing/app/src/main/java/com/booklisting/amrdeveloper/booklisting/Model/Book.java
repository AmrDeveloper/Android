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
    public Book(String bookTitle , String bookImageUrl , String bookPublisher , String bookCategories , String bookDescription)
    {
        this.bookTitle = bookTitle;
        this.bookImageUrl = bookImageUrl;
        this.bookPublisher = bookPublisher;
        this.bookCategories = bookCategories;
        this.bookDescription = bookDescription;
    }

    //Setter and Getter
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(String bookCategories) {
        this.bookCategories = bookCategories;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}
