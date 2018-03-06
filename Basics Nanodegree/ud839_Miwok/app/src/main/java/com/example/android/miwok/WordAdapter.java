package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> objects, int mColorResourceId) {
        super(context, 0, objects);
        this.mColorResourceId = mColorResourceId;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        //
        View view = convertView;
        //check if view equal null
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            //set background for every List
            view.setBackgroundResource(mColorResourceId);
        }

        //Word Object
        Word currentWord = getItem(position);


        //put Miwok Word
        TextView miwok_text_view = (TextView) view.findViewById(R.id.miwok_text_view);
        miwok_text_view.setText(currentWord.getMiwokWord());

        //put Default Word
        TextView english_text_view = (TextView) view.findViewById(R.id.english_text_view);
        english_text_view.setText(currentWord.getDefaultWord());

        //put word image
        ImageView img_id = (ImageView) view.findViewById(R.id.img_id);
        final int image_resource = currentWord.getImageResourceId();

        //Make Zoom for image when user click
        img_id.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Take This Screen Height and width
                DisplayMetrics dispaly = new DisplayMetrics();
                //find this screen
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dispaly);
                //get height
                int height = dispaly.heightPixels;
                //get width
                int width = dispaly.widthPixels;
                //now make inflater
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //inflate layout take my layout id , id of layout that have imageview
                View v = inflater.inflate(R.layout.zoom_layout, (ViewGroup) ((Activity) getContext()).findViewById(R.id.layout));
                //define imageview from this view c
                ImageView img = (ImageView) v.findViewById(R.id.zoom_img);
                //set image in imageview
                img.setImageResource(image_resource);
                //set height of image is height from DisplayMetrics Full Screen
                img.setMinimumHeight(height);
                //set width of image is width from DisplayMetrics Full Screen
                img.setMinimumWidth(width);
                img.setMaxHeight(height);
                img.setMaxWidth(width);
                //show this sub layout in Toast
                Toast toast = new Toast(getContext().getApplicationContext());
                //set view
                toast.setView(v);
                //show this toast
                toast.show();
            }
        });
        //if image Id not equal zero set this image
        if (currentWord.hasImage())
        {
            img_id.setImageResource(image_resource);
            //Make ImageView VISIBLE
            img_id.setVisibility(View.VISIBLE);
        }
        else
        {
            //Make ImageView Gone
            img_id.setVisibility(View.GONE);
        }
        //then return view
        return view;
    }

}
