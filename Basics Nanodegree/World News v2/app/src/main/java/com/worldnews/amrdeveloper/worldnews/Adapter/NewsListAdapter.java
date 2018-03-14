package com.worldnews.amrdeveloper.worldnews.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldnews.amrdeveloper.worldnews.Model.News;
import com.worldnews.amrdeveloper.worldnews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AmrDeveloper on 1/21/2018.
 */

public class NewsListAdapter extends ArrayAdapter<News> {

    public NewsListAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            //Reset View
            view = LayoutInflater.from(getContext()).inflate(R.layout.news_layout, parent, false);
        }

        //Get Current News Object Using Position
        News currentNews = getItem(position);

        //Views init
        ImageView newsImage = view.findViewById(R.id.newsImage);
        TextView newsTitle = view.findViewById(R.id.newsTitle);
        TextView newsPillar = view.findViewById(R.id.newsPillar);
        TextView newsData = view.findViewById(R.id.newsData);
        TextView description = view.findViewById(R.id.description);
        TextView author = view.findViewById(R.id.author);

        //Get This News Bitmap Image
        Bitmap image = currentNews.getImageBitmap();

        if (image != null) {
            newsImage.setImageBitmap(image);
        }

        //Set Current News Information into Views
        newsTitle.setText(currentNews.getTitle());
        newsPillar.setText(currentNews.getPillar());
        newsData.setText(dateFormat(currentNews.getDate()));
        description.setText(currentNews.getShortDescription());
        author.setText(currentNews.getNewsAuthor());

        //Return This View Object
        return view;
    }

    //Format The Date
    private String dateFormat(String date) {
        // This is the time format from guardian JSON "2017-10-29T06:00:20Z"
        // will be changed to 29-10-2017 format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date newDate = format.parse(date);
            format = new SimpleDateFormat("dd-MM-yyyy, h:mm a");
            date = format.format(newDate);
        } catch (ParseException e) {
            Log.e("Adapter", "Problem with parsing the date format");
        }
        return date;
    }

}
