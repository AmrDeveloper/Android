package com.marwa.eltayeb.tweetme;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class QueryUtils {

    //Tag For Debugging
    private final static String LOG_TAG = "NETWORK";

    /**
     * @param apiString The Api Link as String
     * @return Return api link as URL objects
     */
    private static URL createApiUrl(String apiString){
        URL url = null;
        try{
            url = new URL(apiString);
        }
        catch (IOException ex){
            Log.v(LOG_TAG,"Can't Create URL");
        }
        return url;
    }

    /**
     * @param inputStream
     * @return read input Stream and return result on string
     */
    private static String readInputStream(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        catch (Exception e){
            Log.v(LOG_TAG,"Can't Read Stream");
        }
        return stringBuilder.toString();
    }

    /**
     * @param link to open connection with it
     * @return return Json from api
     */
    private static String makeConnection(URL link){
        String jsonResponse = "";
        if(link == null){
            return jsonResponse;
        }
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try
        {
            //Open Connection
            urlConnection = (HttpsURLConnection)link.openConnection();
            //Server Request
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            //Check if connection is done or not
            if(urlConnection.getResponseCode() == 200){
                //Get Input Stream
                inputStream = urlConnection.getInputStream();
                //Reading From Stream using My Method
                jsonResponse = readInputStream(inputStream);
            }
        }
        catch(Exception e){
            Log.v(LOG_TAG, "Cant Open Https Connection");
        }
        finally {
            //On The End Close All Connection
            if(inputStream != null){
                try{
                    inputStream.close();
                }
                catch (IOException e){
                    Log.v(LOG_TAG,"Can't Close Connection");
                }
            }
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    /**
     * @param json
     * @return Return List of news after parsing The json String
     */
    private static List<User> readNewsFromJson(String json){
        //List To Save Data From API
        List<User> newsList = new ArrayList<>();
        Log.v("DATA","Data :" + json);
        return newsList;
    }

    //Public Method to Using With Class To Control all Class
    public static List<User> readDataFromApi(String requestApi){
        //From String to Url
        URL apiUrl = createApiUrl(requestApi);
        //Get Data And Save It On Stream
        String jsonData = makeConnection(apiUrl);
        //Read String and get All News
        return readNewsFromJson(jsonData);
    }
}
