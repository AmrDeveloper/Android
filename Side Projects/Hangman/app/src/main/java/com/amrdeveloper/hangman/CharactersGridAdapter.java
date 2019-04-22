package com.amrdeveloper.hangman;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CharactersGridAdapter extends ArrayAdapter<String> {

    public CharactersGridAdapter(@NonNull Context context,List<String> alphabetChars) {
        super(context, 0, alphabetChars);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.char_grid_item, parent, false);
        }

        TextView hangmanChar = view.findViewById(R.id.hangmanChar);
        hangmanChar.setText(getItem(position));
        return view;
    }
}
