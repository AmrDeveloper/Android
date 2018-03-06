package com.bitprice.amrdeveloper.bitprice;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

/**
 * Created by AmrDeveloper on 12/23/2017.
 */

public class CoinAsyncLoader extends AsyncTaskLoader<CoinInformation> {

    private String api_link;

    public CoinAsyncLoader(Context context , String api_link) {
        super(context);
        this.api_link = api_link;
    }

    @Override
    public CoinInformation loadInBackground() {
        return QueryUtils.getBitCoinInformation(api_link);
    }

    @Override
    protected void onStartLoading() {
        //Make Loader Run
        forceLoad();
        Log.e("Loader","onStartLoading");

    }
}
