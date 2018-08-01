package com.musicapp.amrdeveloper.musicapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.musicapp.amrdeveloper.musicapp.R;
import com.musicapp.amrdeveloper.musicapp.adapter.RecyclerSongAdapter;
import com.musicapp.amrdeveloper.musicapp.model.Song;

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
        songs.add(new Song("Hello", getString(R.string.adele), R.raw.skyfall));
        songs.add(new Song("Set Fire to the Rain", getString(R.string.adele), R.raw.skyfall));
        songs.add(new Song("Send My Love", getString(R.string.adele), R.raw.skyfall));
        songs.add(new Song("Turning Tables", getString(R.string.adele), R.raw.skyfall));
        songs.add(new Song("Million Years Ago", getString(R.string.adele), R.raw.skyfall));
        songs.add(new Song("Skyfall", getString(R.string.adele), R.raw.skyfall));
        songs.add(new Song("All i Ask", getString(R.string.adele), R.raw.skyfall));
        songs.add(new Song("Someone like you", getString(R.string.adele), R.raw.skyfall));

        //Sai Song
        songs.add(new Song("Alive", getString(R.string.sia), R.raw.skyfall));
        songs.add(new Song("Cheap Thrills", getString(R.string.sia), R.raw.skyfall));
        songs.add(new Song("Breathe me", getString(R.string.sia), R.raw.skyfall));
        songs.add(new Song("Elastic Heart", getString(R.string.sia), R.raw.skyfall));
        songs.add(new Song("The Greatest", getString(R.string.sia), R.raw.skyfall));
        songs.add(new Song("You have been loved", getString(R.string.sia), R.raw.skyfall));
        songs.add(new Song("Bring it to me", getString(R.string.sia), R.raw.skyfall));
        songs.add(new Song("I go to sleep", getString(R.string.sia), R.raw.skyfall));

        //Britney Spears Songs
        songs.add(new Song("Criminal", getString(R.string.britney_spears), R.raw.skyfall));
        songs.add(new Song("Gimme More", getString(R.string.britney_spears), R.raw.skyfall));
        songs.add(new Song("Every time", getString(R.string.britney_spears), R.raw.skyfall));
        songs.add(new Song("Crazy", getString(R.string.britney_spears), R.raw.skyfall));
        songs.add(new Song("Some times", getString(R.string.britney_spears), R.raw.skyfall));
        songs.add(new Song("Till The World Ends", getString(R.string.britney_spears), R.raw.skyfall));
        songs.add(new Song("Ooh la la ", getString(R.string.britney_spears), R.raw.skyfall));
        songs.add(new Song("Baby one more time", getString(R.string.britney_spears), R.raw.skyfall));

        //Jennifer Lopes songs
        songs.add(new Song("if you had my love", getString(R.string.jennifer_lopez), R.raw.skyfall));
        songs.add(new Song("all i have", getString(R.string.jennifer_lopez), R.raw.skyfall));
        songs.add(new Song("lets get loud", getString(R.string.jennifer_lopez), R.raw.skyfall));
        songs.add(new Song("Amor amor amor", getString(R.string.jennifer_lopez), R.raw.skyfall));
        songs.add(new Song("No me ames", getString(R.string.jennifer_lopez), R.raw.skyfall));
        songs.add(new Song("First love", getString(R.string.jennifer_lopez), R.raw.skyfall));
        songs.add(new Song("feeling so good", getString(R.string.jennifer_lopez), R.raw.skyfall));
        songs.add(new Song("love don't coast a thing", getString(R.string.jennifer_lopez), R.raw.skyfall));

        //Filter The Big ArrayList
        ArrayList<Song> customSongs = filterSongs(songs, singerName);

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
        for (int i = 0; i < allSongs.size(); i++) {
            Song currentSong = allSongs.get(i);
            String singer = currentSong.getSingerName();
            if (singer.equals(singerName)) {
                customSongs.add(currentSong);
            }
        }
        return customSongs;
    }
}
