package com.amrdeveloper.reposearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView dataListView;
    private EditText requestTag;
    private TextView errorMessage;
    private ProgressBar loadingBar;
    private RepoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingBar = findViewById(R.id.loadingBar);
        errorMessage = findViewById(R.id.errorMessage);
        requestTag = findViewById(R.id.requestTag);
        //ListView And Set Empty View
        dataListView = findViewById(R.id.dataListView);
        dataListView.setEmptyView(errorMessage);

        //Repository Adapter
        adapter = new RepoListAdapter(getApplicationContext());
        dataListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int currentMenuId = item.getItemId();
        if (currentMenuId == R.id.searchMenu) {
            //Should Search Using AsyncTask Class
            //TODO : Call AsyncTask
            String query = requestTag.getText().toString().trim();
            new RepositoryThread().execute(query);
        }
        return true;
    }

    class RepositoryThread extends AsyncTask<String,Void,List<Repository>>{

        @Override
        protected void onPreExecute() {
            loadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Repository> doInBackground(String... strings) {
            if(strings == null || strings.length < 1){
               return null;
            }
            try{
                List<Repository> repositoryList = NetworkUtils.getDataFromApi(strings[0]);
                return repositoryList;
            } catch (IOException ex){
                Log.v("MainThread","IO Exception");
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Repository> repositories) {
            loadingBar.setVisibility(View.GONE);
            if(repositories != null){
                adapter.addAll(repositories);
            }
            else{
                errorMessage.setText(R.string.error_message);
            }
        }
    }
}
