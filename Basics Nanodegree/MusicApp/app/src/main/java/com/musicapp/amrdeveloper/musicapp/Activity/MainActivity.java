package com.musicapp.amrdeveloper.musicapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.musicapp.amrdeveloper.musicapp.R;
import com.musicapp.amrdeveloper.musicapp.adapter.RecyclerSingerAdapter;
import com.musicapp.amrdeveloper.musicapp.model.Singer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView singerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Singer> singer = new ArrayList<>();
        singer.add(new Singer(getString(R.string.adele),R.drawable.adele));
        singer.add(new Singer(getString(R.string.jennifer_lopez),R.drawable.jennifer_lopez));
        singer.add(new Singer(getString(R.string.britney_spears),R.drawable.britney_spears));
        singer.add(new Singer(getString(R.string.sia),R.drawable.sia));

        RecyclerSingerAdapter singerAdapter = new RecyclerSingerAdapter(singer, new RecyclerSingerAdapter.OnItemClickListener() {
            @Override
            public void onClickedItemListener(String singerName) {
                Intent intent = new Intent(MainActivity.this,SongsActivity.class);
                intent.putExtra("singer",singerName);
                startActivity(intent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

        singerList = findViewById(R.id.singerList);
        //Set Recycler View Divider
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        singerList.getContext(),
                        gridLayoutManager.getOrientation());
        singerList.addItemDecoration(dividerItemDecoration);

        singerList.setHasFixedSize(true);
        singerList.setLayoutManager(gridLayoutManager);
        singerList.setAdapter(singerAdapter);
    }
}
