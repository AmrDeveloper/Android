package com.worldnews.amrdeveloper.worldnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldnews.amrdeveloper.worldnews.activities.WebViewerActivity;
import com.worldnews.amrdeveloper.worldnews.model.News;
import com.worldnews.amrdeveloper.worldnews.R;
import com.worldnews.amrdeveloper.worldnews.utils.Utility;

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
        final News currentNews = getItem(position);

        CardView newsItemLayout = view.findViewById(R.id.newsCardView);
        //Views init
        ImageView newsImage = view.findViewById(R.id.newsImage);

        TextView newsTitle = view.findViewById(R.id.newsTitle);
        TextView newsPillar = view.findViewById(R.id.newsPillar);
        TextView newsData = view.findViewById(R.id.newsData);
        TextView description = view.findViewById(R.id.description);
        TextView author = view.findViewById(R.id.author);

        ImageButton shareButton = view.findViewById(R.id.shareButton);

        //Get This News Bitmap Image
        Bitmap image = currentNews.getImageBitmap();

        if (image != null) {
            newsImage.setImageBitmap(image);
        }

        //Set Current News Information into Views
        newsTitle.setText(currentNews.getTitle());
        newsPillar.setText(currentNews.getPillar());
        newsData.setText(Utility.dateFormat(currentNews.getDate()));
        description.setText(currentNews.getShortDescription());
        author.setText(currentNews.getNewsAuthor());

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsUrl = currentNews.getNewsUrl();
                Utility.shareCurrentNews(getContext(),newsUrl);
            }
        });

        newsItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewerActivity.class);
                intent.putExtra("newsUrl",  currentNews.getNewsUrl());
                getContext().startActivity(intent);
            }
        });
        //Return This View Object
        return view;
    }
}
