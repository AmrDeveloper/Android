package com.amrdeveloper.reposearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AmrDeveloper on 2/10/2018.
 */

public class RepoListAdapter extends ArrayAdapter<Repository> {

    //Constructor
    public RepoListAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.repository_item,parent,false);
        }

        TextView repoName = view.findViewById(R.id.repoName);
        TextView repoLang = view.findViewById(R.id.repoLang);
        TextView repoOwner = view.findViewById(R.id.repoOwner);
        TextView repoStars = view.findViewById(R.id.repoStars);

        Repository currentRepository = getItem(position);

        repoName.setText(currentRepository.getName());
        repoLang.setText(currentRepository.getLanguage());
        repoOwner.setText(currentRepository.getOwner());
        repoStars.setText(currentRepository.getStarsCount());

        return view;
    }
}
