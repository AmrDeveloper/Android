package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MasterListFragment extends Fragment {

    private OnImageChangeListener mCallsBack;


    @FunctionalInterface
    public interface OnImageChangeListener{
        void onImageSelected(int position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the Android me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        GridView imagesGridView = rootView.findViewById(R.id.images_grid_view);

        MasterListAdapter masterListAdapter = new MasterListAdapter(getContext(),AndroidImageAssets.getAll());

        imagesGridView.setAdapter(masterListAdapter);

        imagesGridView.setOnItemClickListener((parent, view, position, id) -> {
            mCallsBack.onImageSelected(position);
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
           mCallsBack = (OnImageChangeListener)context;
        }catch (ClassCastException e){
           throw new ClassCastException(context.toString() + " Must Implement OnImageChangeListener");
        }
    }
}
