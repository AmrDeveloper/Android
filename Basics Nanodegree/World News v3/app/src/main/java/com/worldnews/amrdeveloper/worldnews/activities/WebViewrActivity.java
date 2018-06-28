package com.worldnews.amrdeveloper.worldnews.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.worldnews.amrdeveloper.worldnews.R;

public class WebViewrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewr);

        Intent intent = getIntent();
        String url = intent.getStringExtra("newsUrl");
        WebView web = (WebView) findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url);
    }
}
