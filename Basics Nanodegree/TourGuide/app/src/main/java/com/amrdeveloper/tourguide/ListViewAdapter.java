package com.amrdeveloper.tourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by AmrDeveloper on 2/24/2018.
 */

public class ListViewAdapter extends ArrayAdapter<Monument> {

    public ListViewAdapter(@NonNull Context context ) {
        super(context,0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.monument_item,parent,false);
        }

        //Get Current Monument In ListView
        Monument currentMonument = getItem(position);


        //Views Objects To set Monument Model In ListView
        TextView monumentName = view.findViewById(R.id.monumentName);
        ImageView monumentImage = view.findViewById(R.id.monumentImage);
        TextView monumentDescription = view.findViewById(R.id.monumentDescription);

        //Set Information into View Item
        monumentName.setText(currentMonument.getName());

        int imageId =currentMonument.getImageResourceId();
        if(imageId != 0){
            monumentImage.setImageResource(currentMonument.getImageResourceId());
        }

        monumentDescription.setText(currentMonument.getDescription());


        return view;
    }
}
