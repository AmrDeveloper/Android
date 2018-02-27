package com.amrdeveloper.tourguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmrDeveloper on 2/24/2018.
 */

public class ReligiousFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monument_list,container,false);
        //ListView And ListView Adapter
        ListView monumentList = view.findViewById(R.id.monumentList);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext());

        final ArrayList<Monument> monumentArrayList = new ArrayList<>();

        monumentArrayList.add(new Monument(getResources().getString(R.string.mount_sinai_name),getResources().getString(R.string.mount_sinai_description),R.drawable.mount_sinai));
        monumentArrayList.add(new Monument(getResources().getString(R.string.hanging_church),getResources().getString(R.string.hanging_church_description),R.drawable.hanging_church));
        monumentArrayList.add(new Monument(getResources().getString(R.string.azhar_mosque_name),getResources().getString(R.string.azhar_mosque_description),R.drawable.azhar_mosque));
        monumentArrayList.add(new Monument(getResources().getString(R.string.saint_catherine_name),getResources().getString(R.string.saint_catherine_description),R.drawable.saint_catherine));

        listViewAdapter.addAll(monumentArrayList);
        listViewAdapter.notifyDataSetChanged();
        monumentList.setAdapter(listViewAdapter);

        return view;
    }
}
