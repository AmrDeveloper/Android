package com.bitprice.amrdeveloper.bitprice;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by AmrDeveloper on 12/23/2017.
 */

public class QueryUtils {

    //Constructor
    QueryUtils(){

    }

    //Take Api Link and convert it to URL Object
    private static URL convertLinkToUrl(String link){
        URL url = null;
        //Try To Convert
        try {
            url = new URL(link);
        }
        catch (MalformedURLException e) {
            Log.v("QueryUtils","Can't Convert String to URL");
        }
        return url;
    }

    //Read InputStream and return it on String
    private static String readInputStream(InputStream stream){
        //Using String Builder to Append String to other
        StringBuilder builder = new StringBuilder();
        //Check If InputStream not equal null
        if(stream != null){
            InputStreamReader streamReader = new InputStreamReader(stream);
            //Read Stream as Chars
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            //Reading Line By Line
            try
            {
                String line = bufferedReader.readLine();
                //Read All Stream
                while(line != null){
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
            }
            catch (IOException e) {
                Log.v("Utils","Cant Read Input Stream");
            }
        }
        return builder.toString();
    }

    //open HTTP Connection with url as input
    private static String openHttpConnection(URL url){
        HttpsURLConnection connection = null;
        InputStream stream = null;
        String jsonData = "";

        try{
            //Open Connection
            connection = (HttpsURLConnection)url.openConnection();
            //Using GET to request data from server
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(10000);
            connection.connect();
            //200 mean it connection is Start
            if(connection.getResponseCode() == 200)
            {
                //Get InputStream From URL
                stream = connection.getInputStream();
                //Convert InputStream to String
                jsonData = readInputStream(stream);
            }
        }
        catch (Exception e)
        {
            Log.v("Utils","Connection Error");
        }
        finally
        {
            //Finally Close Connection if it not equal null
            if(connection != null){
                connection.disconnect();
            }
            if(stream != null){
                try{
                    stream.close();
                }
                catch (IOException ioEx){
                    Log.v("Utils","IO Exception when close stream");
                }
            }
        }
        //return data
        return jsonData;
    }

    private static CoinInformation readBitCoinInformationFromJson(String jsonData){
        CoinInformation info = null;
        if(jsonData.equals("")){
            return null;
        }

        //Try To reading
        try
        {
            JSONObject jsonResponse = new JSONObject(jsonData);
            JSONObject root = jsonResponse.getJSONObject("bpi");

            // UDS Object
            JSONObject udsPriceObject = root.getJSONObject("USD");
            String udsPrice = udsPriceObject.getString("rate_float");

            // GBP Object
            JSONObject gbpPriceObject = root.getJSONObject("GBP");
            String gbpPrice = gbpPriceObject.getString("rate_float");

            // EUR Object
            JSONObject eurPriceObject = root.getJSONObject("EUR");
            String eurPrice = eurPriceObject.getString("rate_float");

            //Save Data on Model Object
            info = new CoinInformation(udsPrice,
                                       gbpPrice,
                                       eurPrice);
        }
        catch (JSONException jsonex){
            Log.v("Utils","can't Read Json");
        }
        return info;
    }

    //Public Method to reading
    public static CoinInformation getBitCoinInformation(String api_link){
        //Convert To Url
        URL url = convertLinkToUrl(api_link);
        String jsonData = openHttpConnection(url);
        CoinInformation coinInfo = readBitCoinInformationFromJson(jsonData);
        return coinInfo;
    }


}
