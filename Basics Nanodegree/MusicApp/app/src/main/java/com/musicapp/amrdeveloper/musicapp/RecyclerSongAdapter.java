package com.musicapp.amrdeveloper.musicapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by AmrDeveloper on 4/3/2018.
 */

public class RecyclerSongAdapter extends RecyclerView.Adapter<RecyclerSongAdapter.SongViewHolder>{

    private List<Song> mSongList;

    public RecyclerSongAdapter(List<Song> mSongList){
        this.mSongList = mSongList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.song_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song currentSong = mSongList.get(position);

        final String songName = currentSong.getSongName();
        final String singerName = currentSong.getSingerName();
        final int songId = currentSong.getSongResourceId();

        holder.songName.setText(songName);
        holder.songPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Context context = v.getContext();
                Intent intent = new Intent(context,PlayerActivity.class);
                //Put Information into Intent
                intent.putExtra("songName",songName);
                intent.putExtra("singerName",singerName);
                intent.putExtra("songId",songId);
                //Start Intent
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder{

        private TextView songName;
        private ImageButton songPlayButton;

        public SongViewHolder(View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.songName);
            songPlayButton = itemView.findViewById(R.id.songPlayButton);
        }
    }
}
