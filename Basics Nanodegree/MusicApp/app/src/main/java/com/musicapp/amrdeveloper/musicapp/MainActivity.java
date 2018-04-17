package com.musicapp.amrdeveloper.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView singerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Singer> singer = new ArrayList<>();
        singer.add(new Singer("Adele",R.drawable.adele));
        singer.add(new Singer("Jennifer Lopez",R.drawable.jennifer_lopez));
        singer.add(new Singer("Britney Spears",R.drawable.britney_spears));
        singer.add(new Singer("Sia",R.drawable.sia));

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
