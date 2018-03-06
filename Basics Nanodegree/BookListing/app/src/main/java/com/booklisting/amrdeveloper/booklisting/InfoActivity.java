package com.booklisting.amrdeveloper.booklisting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private TextView book_title;
    private TextView book_cate;
    private TextView book_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        book_title = findViewById(R.id.book_title);
        book_cate = findViewById(R.id.book_cate);
        book_desc = findViewById(R.id.book_desc);

        Intent intent = getIntent();
        String bookTitle = intent.getStringExtra("title");
        if(bookTitle.equals("")){
           bookTitle = "no Title";
        }
        String bookCategories = "Categories" + intent.getStringExtra("cate");
        if(bookCategories.equals("")){
            bookTitle = "no Categories";
        }
        String bookDescription = intent.getStringExtra("desc");
        if(bookDescription.equals("")){
            bookTitle = "no Description";
        }

        book_title.setText(bookTitle);
        book_cate.setText(bookCategories);
        book_desc.setText(bookDescription);


    }
}
