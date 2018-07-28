package com.worldnews.amrdeveloper.worldnews.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.worldnews.amrdeveloper.worldnews.R;
import com.worldnews.amrdeveloper.worldnews.utils.Utility;

public class WebViewerActivity extends AppCompatActivity {

    private String currentNewsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewr);

        Intent intent = getIntent();
        this.currentNewsUrl = intent.getStringExtra("newsUrl");

        WebView web = findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(currentNewsUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate Share Menu
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.share_menu) {
            //Share Current news
            Utility.shareCurrentNews(getApplicationContext(),currentNewsUrl);
        }
        return true;
    }
}
