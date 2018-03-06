package com.bitprice.amrdeveloper.bitprice;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<CoinInformation>{

    //API Link To get Current Bitcoin Price
    private final String BITCOIN_API = "https://api.coindesk.com/v1/bpi/currentprice.json";

    //TextView For Every Price
    private TextView usd_price_text;
    private TextView euro_price_text;
    private TextView gps_price_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        usd_price_text = (TextView)findViewById(R.id.usd_price_text);
        euro_price_text = (TextView)findViewById(R.id.euro_price_text);
        gps_price_text = (TextView)findViewById(R.id.gps_price_text);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            //Initialization Loader
            getLoaderManager().initLoader(0,null,this);
        }
        else{
            Toast.makeText(this, "No Internet :D", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public Loader<CoinInformation> onCreateLoader(int i, Bundle bundle) {
        //Take Context and api link
        Loader<CoinInformation> informationLoader = new CoinAsyncLoader(this,BITCOIN_API);
        //Then Return The Loader
        return informationLoader;
    }

    @Override
    public void onLoadFinished(Loader<CoinInformation> loader, CoinInformation coinInformation) {
        if(coinInformation == null){
            return;
        }
        //Update UI
        String usd_price = "USD Price : " + coinInformation.getPriceUSD();
        usd_price_text.setText(usd_price);

        String eur_price = "EUR Price : " + coinInformation.getPriceEUR();
        euro_price_text.setText(eur_price);

        String gps_price = "GBP Price : " + coinInformation.getPriceGBP();
        gps_price_text.setText(gps_price);
    }

    @Override
    public void onLoaderReset(Loader<CoinInformation> loader) {

    }
}
