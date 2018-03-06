package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by AmrDeveloper on 11/26/2017.
 */

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    private String apiUrl;

    public EarthQuakeLoader(Context context , String apiUrl) {
        super(context);
        this.apiUrl = apiUrl;
    }

    @Override
    public List<EarthQuake> loadInBackground() {

        //Get Data And Store it on list
        List<EarthQuake> reportList = QueryUtils.fetchEarthquakeData(apiUrl);
        Log.e("Loader","loadInBackground");
        //Return this list
        return reportList;
    }

    @Override
    protected void onStartLoading() {
        //Make Loader Run
        forceLoad();
        Log.e("Loader","onStartLoading");

    }
}
