package amrdeveloper.com.tweet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

/**
 * Created by AmrDeveloper on 7/14/2018.
 */

public class TweetResultReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();

        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success
            final Long tweetId = intentExtras.getLong(TweetUploadService.EXTRA_TWEET_ID);
            Toast.makeText(context, "Tweet Id : " + tweetId, Toast.LENGTH_SHORT).show();
        } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {
            // failure
            final Intent retryIntent = intentExtras.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
            Toast.makeText(context, "Can't Send This Tweet", Toast.LENGTH_SHORT).show();
        } else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {
            Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
