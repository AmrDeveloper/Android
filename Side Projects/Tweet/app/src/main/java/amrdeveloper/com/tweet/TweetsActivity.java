package amrdeveloper.com.tweet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TweetsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private MaterialSearchView searchView;
    private Toolbar mainToolbar;
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);


        recyclerView = findViewById(R.id.tweetsRecyclerView);

        // Set layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Add Item Decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        setUpTweets();

        mainToolbar = findViewById(R.id.toolbar);
        //Set Toolbar Title Color
        mainToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        //Set ToolBar as Action Bar
        setSupportActionBar(mainToolbar);
        //Set Title For Tool Bar
        getSupportActionBar().setTitle(R.string.app_name);
        //Init SearchView
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                tweetsSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
    }

    private void tweetsSearch(String query) {
        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query(query)
                .build();

        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(getApplicationContext())
                        .setTimeline(searchTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();

        recyclerView.setAdapter(adapter);
    }

    public void sendTweetDialog(View view) {
        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();
        final Intent intent = new ComposerActivity.Builder(TweetsActivity.this)
                .session(session)
                .text("Love where you work")
                .hashtags("#twitter")
                .createIntent();
        startActivity(intent);
    }

    private void setUpTweets(){
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(username)
                .includeReplies(true)
                .includeRetweets(true)
                .build();

        final TweetTimelineRecyclerViewAdapter adapter = new TweetTimelineRecyclerViewAdapter.Builder(this)
                .setTimeline(userTimeline)
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build();

        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        setUpTweets();
    }
}
