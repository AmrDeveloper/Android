package amrdeveloper.com.tweet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

/**
 * Created by AmrDeveloper on 7/14/2018.
 */

public class TweetResultReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        /*
        Bundle intentExtras;

        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction()))
        {
            // success
            final Long tweetId = intentExtras.getLong(TweetUploadService.EXTRA_TWEET_ID);
        }
        else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction()))
        {
            // failure
            final Intent retryIntent = intentExtras.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
        }
        else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {
            // cancel
        }
        */
    }
}
