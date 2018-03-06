package com.booklisting.amrdeveloper.booklisting;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.booklisting.amrdeveloper.booklisting.Adapter.BooksListAdapter;
import com.booklisting.amrdeveloper.booklisting.Model.Book;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //api request url
    private String requestUrl;
    //GridView for display data
    private GridView dataList;
    //Main Tool Bar on Application
    private Toolbar mainToolbar;
    //Search View To Search on Data
    private MaterialSearchView searchview;
    //Book Adapter
    private BooksListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Take The last Url After This Activity Destroyed
        if(savedInstanceState != null){
            this.requestUrl = savedInstanceState.getString("api_url");
        }

        //Grid View
        dataList = (GridView)findViewById(R.id.dataList);
        //Set Empty View
        dataList.setEmptyView((TextView)findViewById(R.id.empty_view));
        //Set On item Click Listener
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //When User Click , using intent and go to InfoActivity
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                Book currentBook = adapter.getItem(i);
                intent.putExtra("title",currentBook.getBookTitle());
                intent.putExtra("cate",currentBook.getBookCategories());
                intent.putExtra("desc",currentBook.getBookDescription());
                //Start This Intent
                startActivity(intent);
            }
        });
        //Adapter Object
        adapter = new BooksListAdapter(this,new ArrayList<Book>());

        //set Adapter
        dataList.setAdapter(adapter);
        //Main Tool Bar
        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        //Set Title For Tool Bar
        getSupportActionBar().setTitle(R.string.app_name);
        //Set Title Color For Tool Bar
        //mainToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        //Init SearchView
        searchview = (MaterialSearchView) findViewById(R.id.search_view);


        //Get String to Search
        searchview.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String bookTag = query.replaceAll(" ","%20").trim();
                String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + bookTag + "&maxResults=26";
                requestUrl = apiUrl;
                new MyAsyncTask().execute(apiUrl);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    //My Async Task Class
    class MyAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... strings) {
            if ((strings == null) || (strings.length < 0)) {
                return null;
            }
            //Get Data And Save It on This List
            List<Book> bookList = QueryUtils.getDataFromInternet(strings[0]);
            return bookList;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            //Clear Last Data
            adapter.clear();
            //Put All List on Adapter
            adapter.addAll(books);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflater The Menu File
        getMenuInflater().inflate(R.menu.search_menu, menu);
        //Get Menu
        MenuItem item = menu.findItem(R.id.search_icon);
        //Set This Menu on SearchView
        searchview.setMenuItem(item);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("api_url",requestUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(requestUrl != null){
            new MyAsyncTask().execute(requestUrl);
        }
    }

}
