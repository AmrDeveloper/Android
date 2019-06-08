package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {

    private List<Integer> mImageIds;
    private int mListIndex;

    private static final String TAG = "BodyPartFragment";
    private static final String LAST_INDEX = "last_index";
    private static final String IMAGE_ID_LIST = "images_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Load saved state
        if(savedInstanceState != null){
            mListIndex = savedInstanceState.getInt(LAST_INDEX,0);
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
        }

        //Inflate the Android me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        //init body part image view
        ImageView imageView = rootView.findViewById(R.id.body_part_image_View);
        imageView.setImageResource(mImageIds.get(mListIndex));
        if(mImageIds != null){
            imageView.setOnClickListener(v -> {
                if(mListIndex < mImageIds.size() - 1){
                    mListIndex++;
                }else{
                    mListIndex = 0;
                }
                imageView.setImageResource(mImageIds.get(mListIndex));
            });
        }else{
            Log.v(TAG,"This fragment has null list of image id's");
        }

        return rootView;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.mImageIds = imageIds;
    }

    public void setListIndex(int index) {
        this.mListIndex = index;
    }

    /*
     * Save the current state
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAST_INDEX,mListIndex);
        outState.putIntegerArrayList(IMAGE_ID_LIST,(ArrayList<Integer>) mImageIds);
    }
}
