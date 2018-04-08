package com.musicapp.amrdeveloper.musicapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AmrDeveloper on 4/3/2018.
 */

public class RecyclerSingerAdapter extends RecyclerView.Adapter<RecyclerSingerAdapter.SingerViewHolder> {

    private List<Singer> mSingerList;
    private final OnItemClickListener onItemClickListener;

    //InterFace for Implements OnClickListener
    public interface OnItemClickListener {
        void onClickedItemListener(String singerName);
    }


    public RecyclerSingerAdapter(List<Singer> mSingerList , OnItemClickListener onItemClickListener) {
        this.mSingerList = mSingerList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_list_block;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new SingerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerViewHolder holder, int position) {
        Singer currentSinger = mSingerList.get(position);
        String name = currentSinger.getSingerName();
        int imageId = currentSinger.getSingerImageId();

        holder.singerName.setText(name);
        if (currentSinger.singerHasImage()) {
            holder.singerImage.setImageResource(imageId);
        }
    }

    @Override
    public int getItemCount() {
        return mSingerList.size();
    }

    class SingerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView singerImage;
        private TextView singerName;

        public SingerViewHolder(View itemView) {
            super(itemView);

            this.singerImage = itemView.findViewById(R.id.singerImage);
            this.singerName = itemView.findViewById(R.id.singerName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String Name = singerName.getText().toString();
            onItemClickListener.onClickedItemListener(Name);
        }
    }
}
