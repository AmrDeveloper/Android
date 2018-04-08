package com.musicapp.amrdeveloper.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SongsActivity extends AppCompatActivity {

    private RecyclerView songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        Intent intent = getIntent();
        //Get Singer Name
        String singerName = intent.getStringExtra("singer");

        //All Music
        final ArrayList<Song> songs = new ArrayList<>();

        songs.add(new Song("Skyfall", "Adele", 0));
        songs.add(new Song("Sia", "Sia", 0));
        songs.add(new Song("Hello", "Adele", 0));
        songs.add(new Song("Skyfall", "Adele", 0));
        songs.add(new Song("Sia", "Sia", 0));
        songs.add(new Song("Hello", "Adele", 0));
        songs.add(new Song("Skyfall", "Adele", 0));
        songs.add(new Song("Sia", "Sia", 0));
        songs.add(new Song("Hello", "Adele", 0));
        songs.add(new Song("Skyfall", "Adele", 0));
        songs.add(new Song("Sia", "Sia", 0));
        songs.add(new Song("Hello", "Adele", 0));

        //Filter The Big ArrayList
        ArrayList<Song> customSongs = filterSongs(songs,singerName);

        RecyclerSongAdapter adapter = new RecyclerSongAdapter(customSongs);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        songsList = findViewById(R.id.songsList);
        songsList.setLayoutManager(linearLayoutManager);
        songsList.setHasFixedSize(true);
        songsList.setAdapter(adapter);
    }


    private ArrayList<Song> filterSongs(ArrayList<Song> allSongs, String singerName) {
        ArrayList<Song> customSongs = new ArrayList<>();
        for(int i = 0 ; i < allSongs.size() ; i++){
            Song currentSong = allSongs.get(i);
            String singer = currentSong.getSingerName();
            if(singer.equals(singerName)){
                customSongs.add(currentSong);
            }
        }
        return customSongs;
    }
}
