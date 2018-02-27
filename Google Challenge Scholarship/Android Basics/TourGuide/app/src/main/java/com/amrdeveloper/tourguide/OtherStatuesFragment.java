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

public class OtherStatuesFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monument_list,container,false);
        //ListView And ListView Adapter
        ListView monumentList = view.findViewById(R.id.monumentList);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext());

        final ArrayList<Monument> monumentArrayList = new ArrayList<>();

        monumentArrayList.add(new Monument(getResources().getString(R.string.ras_muhammad_np_name),getResources().getString(R.string.ras_muhammad_np_description),R.drawable.ras_muhammad_np));
        monumentArrayList.add(new Monument(getResources().getString(R.string.khan_el_khalili_name),getResources().getString(R.string.khan_el_khalili_description),R.drawable.khan_el_khalili));
        monumentArrayList.add(new Monument(getResources().getString(R.string.saladin_citadel_name),getResources().getString(R.string.saladin_citadel_description),R.drawable.saladin_citadel));
        monumentArrayList.add(new Monument(getResources().getString(R.string.qaitbay_name),getResources().getString(R.string.qaitbay_description),R.drawable.qaitbay));

        listViewAdapter.addAll(monumentArrayList);
        listViewAdapter.notifyDataSetChanged();
        monumentList.setAdapter(listViewAdapter);

        return view;
    }
}
