package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by AmrDeveloper on 11/21/2017.
 */

public class QueryUtils{
    //Boundary Class To Parser Json
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    QueryUtils( ){
    }

    // TODO : Convert String to URL
    private static URL createUrl(String link){
        //Create new Object with null Value
        URL url = null;
        //Try To Convert String to Url
        try{
            url = new URL(link);
        }
        //Catch MalformedURLException if convert fail
        catch (MalformedURLException e ){
            Log.e("Utils","Cant Convert String to URL");
        }
        return url;
    }

    // TODO: Take InputStream read it and return String
    private static String readFromStream(InputStream stream) throws IOException {
        //Create StringBuilder To Easy and fast to append
        StringBuilder output = new StringBuilder();
        //if This Stream its null
        if(stream != null){
            //Encoding this stream
            InputStreamReader readerStream = new InputStreamReader(stream, Charset.forName("UTF-8"));
            //Reading it using BufferedReader
            BufferedReader reader = new BufferedReader(readerStream);
            //read line by line
            String line = reader.readLine();
            //while this line not null
            while (line != null){
                //append this line to output
                output.append(line);
                //read again
                line = reader.readLine();
            }
        }
        //return StringBuilder As String
        return output.toString();
    }

    // TODO: Make Http Connection
    private static String makeConnection(URL requestUrl) throws IOException {
        //The Json Link
        String jsonResponse = "";
        //check if this link is null
        if(requestUrl == null){
            return jsonResponse;
        }

        //Make Connection
        //http Connection Object
        HttpsURLConnection urlConnection = null;
        //The InputStream
        InputStream stream = null;
        //Try To Make Connection
        try{
            //Open Connection from this Url
            urlConnection = (HttpsURLConnection)requestUrl.openConnection();
            //Set Server Method -- > GET to reading
            urlConnection.setRequestMethod("GET");
            //set Readingtime out
            urlConnection.setReadTimeout(10000);
            //set Connection time out
            urlConnection.setConnectTimeout(15000);
            //now open connection
            urlConnection.connect();
            //check if request is done or not
            //200 for done and 400 for fail
            if(urlConnection.getResponseCode() == 200){
                //get input Stream
                stream = urlConnection.getInputStream();
                //reading ths stream on string
                jsonResponse = readFromStream(stream);
            }
        }
        catch (Exception e){
            Log.v("Utils","Connection Fail");
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (stream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                stream.close();
            }
        }
        return jsonResponse;
    }

    // TODO; JSON Parser
    private static List<EarthQuake> extractFeatureFromJson(String json){
        //check if it null
        if(TextUtils.isEmpty(json)){
           return null;
        }
        //List or Reports
        List<EarthQuake> reportList = new ArrayList<>();
        //try to parser
        try{
            // Create a JSONObject from the JSON response string
            JSONObject JsonResponse = new JSONObject(json);

            //Make JSON Object
            //features on EarthQuake API
            JSONArray root = JsonResponse.getJSONArray("features");
            //read every report
            for(int i = 0 , len = root.length() ; i < len ; i++){
                //current EarthQuake
                JSONObject currentReport = root.getJSONObject(i);
                //properties json object
                JSONObject properties = currentReport.getJSONObject("properties");
                //get mag , place , time , site
                // Extract the value for the key called "mag"
                double magnitude = properties.getDouble("mag");

                // Extract the value for the key called "place"
                String location = properties.getString("place");

                // Extract the value for the key called "time"
                long time = properties.getLong("time");

                // Extract the value for the key called "url"
                String url = properties.getString("url");

                //create new object and add it on list
                EarthQuake report = new EarthQuake(magnitude,location,time,url);
                //add this report to list
                reportList.add(report);
            }
        }
        catch (Exception e){
            Log.e("Utils","Parser Fail");
        }
        //return all report as list
        return reportList;
    }

    // TODO: method to control all this class
    public static List<EarthQuake> fetchEarthquakeData(String requestUrl){
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeConnection(url);
        }
        catch (IOException e) {
            Log.e("Utils", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<EarthQuake> earthquakes = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }
}
