package com.musicapp.amrdeveloper.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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
        //Adele Songs
        songs.add(new Song("Hello", "Adele", R.raw.skyfall));
        songs.add(new Song("Set Fire to the Rain", "Adele", R.raw.skyfall));
        songs.add(new Song("Send My Love", "Adele", R.raw.skyfall));
        songs.add(new Song("Turning Tables", "Adele", R.raw.skyfall));
        songs.add(new Song("Milion Years Ago", "Adele", R.raw.skyfall));
        songs.add(new Song("Skyfall", "Adele", R.raw.skyfall));
        songs.add(new Song("All i Ask", "Adele", R.raw.skyfall));
        songs.add(new Song("Someone like you", "Adele", R.raw.skyfall));

        //Sai Song
        songs.add(new Song("Alive", "Sia", R.raw.skyfall));
        songs.add(new Song("Cheap Thrills", "Sia", R.raw.skyfall));
        songs.add(new Song("Breathe me", "Sia", R.raw.skyfall));
        songs.add(new Song("Elastic Heart", "Sia", R.raw.skyfall));
        songs.add(new Song("The Greatest", "Sia", R.raw.skyfall));
        songs.add(new Song("You have been loved", "Sia", R.raw.skyfall));
        songs.add(new Song("Bring it to me", "Sia", R.raw.skyfall));
        songs.add(new Song("I go to sleep", "Sia", R.raw.skyfall));

        //Britney Spears Songs
        songs.add(new Song("Criminal", "Britney Spears", R.raw.skyfall));
        songs.add(new Song("Gimme More", "Britney Spears", R.raw.skyfall));
        songs.add(new Song("Every time", "Britney Spears", R.raw.skyfall));
        songs.add(new Song("Crazy", "Britney Spears", R.raw.skyfall));
        songs.add(new Song("Some times", "Britney Spears", R.raw.skyfall));
        songs.add(new Song("Till The World Ends", "Britney Spears", R.raw.skyfall));
        songs.add(new Song("Ooh la la ", "Britney Spears", R.raw.skyfall));
        songs.add(new Song("Baby one more time", "Britney Spears", R.raw.skyfall));

        //Jennifer Lopes songs
        songs.add(new Song("if you had my love", "Jennifer Lopez", R.raw.skyfall));
        songs.add(new Song("all i have", "Jennifer Lopez", R.raw.skyfall));
        songs.add(new Song("lets get loud", "Jennifer Lopez", R.raw.skyfall));
        songs.add(new Song("Amor amor amor", "Jennifer Lopez", R.raw.skyfall));
        songs.add(new Song("No me ames", "Jennifer Lopez", R.raw.skyfall));
        songs.add(new Song("First love", "Jennifer Lopez", R.raw.skyfall));
        songs.add(new Song("feeling so good", "Jennifer Lopez", R.raw.skyfall));
        songs.add(new Song("love don't coast a thing", "Jennifer Lopez", R.raw.skyfall));

        //Filter The Big ArrayList
        ArrayList<Song> customSongs = filterSongs(songs,singerName);

        RecyclerSongAdapter adapter = new RecyclerSongAdapter(customSongs);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        songsList = findViewById(R.id.songsList);
        //Set Recycler View Divider
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        songsList.getContext(),
                        linearLayoutManager.getOrientation());
        songsList.addItemDecoration(dividerItemDecoration);
        songsList.setLayoutManager(linearLayoutManager);
        songsList.setHasFixedSize(true);
        songsList.setAdapter(adapter);
    }

    //Simple Method to Filer Songs and return one singer song
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
