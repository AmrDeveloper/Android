package com.amrdeveloper.tourguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by AmrDeveloper on 2/24/2018.
 */

public class ModernFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monument_list,container,false);
        //ListView And ListView Adapter
        ListView monumentList = view.findViewById(R.id.monumentList);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext());

        final ArrayList<Monument> monumentArrayList = new ArrayList<>();

        monumentArrayList.add(new Monument(getResources().getString(R.string.bibliotheca_alex_name),getResources().getString(R.string.bibliotheca_alex_description),R.drawable.bibliotheca_alex));
        monumentArrayList.add(new Monument(getResources().getString(R.string.egyptian_museum_name),getResources().getString(R.string.egyptian_museum_description),R.drawable.egyptian_museum));
        monumentArrayList.add(new Monument(getResources().getString(R.string.cairo_tower_name),getResources().getString(R.string.cairo_tower_description),R.drawable.cairo_tower));
        monumentArrayList.add(new Monument(getResources().getString(R.string.luxor_museum_name),getResources().getString(R.string.luxor_museum_description),R.drawable.luxor_museum));

        listViewAdapter.addAll(monumentArrayList);
        listViewAdapter.notifyDataSetChanged();
        monumentList.setAdapter(listViewAdapter);

        return view;
    }
}
