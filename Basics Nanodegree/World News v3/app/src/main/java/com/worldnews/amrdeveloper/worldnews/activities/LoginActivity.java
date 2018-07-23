package com.worldnews.amrdeveloper.worldnews.activities;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.worldnews.amrdeveloper.worldnews.R;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private TwitterLoginButton twitterLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_login);
        isUserLogin();

        twitterLoginButton = findViewById(R.id.twitterLoginButton);
        twitterLoginButton.setBackgroundColor(getResources().getColor(R.color.darkRed));
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                userLoginSuccess(session);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(LoginActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @param session : TwitterSession To get user information if login is success
     */
    private void userLoginSuccess(TwitterSession session) {
        String username = session.getUserName();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void isUserLogin() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        try {
            String username = session.getUserName();
            if (!TextUtils.isEmpty(username)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } catch (NullPointerException e) {
            Log.v(LOG_TAG, "No username");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
