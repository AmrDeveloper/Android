package com.marwa.eltayeb.tweetme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private List<User> userList;

    public UserAdapter(@NonNull Context context, List<User> userList) {
        super(context, 0,userList);
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            //Reset View
            view = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);
        }

        User currentUser = getItem(position);

        TextView userName = view.findViewById(R.id.userNameTxt);
        TextView userAddress = view.findViewById(R.id.userAddressTxt);

        userName.setText(currentUser.getName());
        userAddress.setText(currentUser.getAddress());

        return view;
    }
}
