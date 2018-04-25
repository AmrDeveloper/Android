package com.booklisting.amrdeveloper.booklisting.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.booklisting.amrdeveloper.booklisting.Adapter.BooksListAdapter;
import com.booklisting.amrdeveloper.booklisting.Loaders.BooksAsyncLoader;
import com.booklisting.amrdeveloper.booklisting.Model.Book;
import com.booklisting.amrdeveloper.booklisting.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>> {

    //api request url
    private String requestUrl;
    //GridView for display data
    private GridView dataGridView;
    //Main Tool Bar on Application
    private Toolbar mainToolbar;
    //Search View To Search on Data
    private MaterialSearchView searchview;
    //Book Adapter
    private BooksListAdapter adapter;
    //The Current Loader final ID
    private static final int BOOK_SEARCH_LOADER = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Take The last Url After This Activity Destroyed
        if (savedInstanceState != null) {
            this.requestUrl = savedInstanceState.getString("api_url");
        }

        //Grid View
        dataGridView = findViewById(R.id.dataList);
        //Set Empty View
        dataGridView.setEmptyView(findViewById(R.id.empty_view));
        //Set On item Click Listener
        dataGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //When User Click , using intent and go to InfoActivity
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                Book currentBook = adapter.getItem(i);
                //Moving the data to next Activity
                intent.putExtra("title", currentBook.getBookTitle());
                intent.putExtra("cate", currentBook.getBookCategories());
                intent.putExtra("desc", currentBook.getBookDescription());
                //Start This Intent
                startActivity(intent);
            }
        });
        //Adapter Object
        adapter = new BooksListAdapter(this, new ArrayList<Book>());

        //set Adapter
        dataGridView.setAdapter(adapter);
        //Main Tool Bar
        mainToolbar = findViewById(R.id.toolbar);
        //Set Toolbar Title Color
        mainToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        //Set ToolBar as Action Bar
        setSupportActionBar(mainToolbar);
        //Set Title For Tool Bar
        getSupportActionBar().setTitle(R.string.app_name);
        //Init SearchView
        searchview = findViewById(R.id.search_view);

        //Get String to Search
        searchview.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String bookTag = query.replaceAll(" ", "%20").trim();
                requestUrl = "https://www.googleapis.com/books/v1/volumes?q=" + bookTag + "&maxResults=26";

                makeBooksSearchQuery();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Run Only If Search Url not equal null to avoid from NullPointerException
        if (requestUrl != null) {
            getSupportLoaderManager().initLoader(BOOK_SEARCH_LOADER, null, this);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BooksAsyncLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        //Clear Old Data From ListView
        adapter.clear();
        //Put new Data in ListView
        adapter.addAll(books);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loaders) {

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
        outState.putString("api_url", requestUrl);
    }

    private void makeBooksSearchQuery() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString("QUERY", requestUrl);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(BOOK_SEARCH_LOADER);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(BOOK_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(BOOK_SEARCH_LOADER, queryBundle, this);
        }
    }
}
