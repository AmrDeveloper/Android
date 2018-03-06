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

public class PharaonicFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monument_list,container,false);
        //ListView And ListView Adapter
        ListView monumentList = view.findViewById(R.id.monumentList);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext());

        final ArrayList<Monument> monumentArrayList = new ArrayList<>();

        monumentArrayList.add(new Monument(getResources().getString(R.string.pyramids_name),getResources().getString(R.string.pyramids_description),R.drawable.pyramids));
        monumentArrayList.add(new Monument(getResources().getString(R.string.pyramid_of_djoser_name),getResources().getString(R.string.pyramid_of_djoser_description),R.drawable.pyramid_of_djoser));
        monumentArrayList.add(new Monument(getResources().getString(R.string.sphinx_name),getResources().getString(R.string.sphinx_description),R.drawable.sphinx));
        monumentArrayList.add(new Monument(getResources().getString(R.string.abu_simbel_name),getResources().getString(R.string.abu_simbel_description),R.drawable.abu_simbel));
        monumentArrayList.add(new Monument(getResources().getString(R.string.valley_of_the_kings_name),getResources().getString(R.string.valley_of_the_kings_description),R.drawable.valley_of_the_kings));


        listViewAdapter.addAll(monumentArrayList);
        listViewAdapter.notifyDataSetChanged();
        monumentList.setAdapter(listViewAdapter);

        return view;
    }
}
