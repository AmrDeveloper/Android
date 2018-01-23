package com.worldnews.amrdeveloper.worldnews.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldnews.amrdeveloper.worldnews.Model.News;
import com.worldnews.amrdeveloper.worldnews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AmrDeveloper on 1/21/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context) {
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
        News currentNews = getItem(position);

        ImageView newsImage = (ImageView) view.findViewById(R.id.newsImage);
        TextView newsTitle = (TextView) view.findViewById(R.id.newsTitle);
        TextView newsPillar = (TextView) view.findViewById(R.id.newsPillar);
        TextView newsData = (TextView) view.findViewById(R.id.newsData);

        if(!currentNews.getImageUrl().equals("")){
            Picasso.with(getContext()).load(currentNews.getImageUrl()).fit().into(newsImage);
        }
        else
        {
            newsImage.setVisibility(View.GONE);
        }

        newsTitle.setText(currentNews.getTitle());
        newsPillar.setText(currentNews.getPillar());
        newsData.setText(formatDate(currentNews.getDate()));

        return view;
    }

    //Format The Date
    private static String formatDate(String currentDate) {
        // This is the time format from guardian JSON "2017-10-29T06:00:20Z"
        // will be changed to 29-10-2017 format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date newDate = format.parse(currentDate);
            format = new SimpleDateFormat("dd-MM-yyyy, h:mm a");
            currentDate = format.format(newDate);
        }catch (ParseException e){
            Log.e("Adapter","Problem with parsing the date format");
        }
        return currentDate;
    }
}
