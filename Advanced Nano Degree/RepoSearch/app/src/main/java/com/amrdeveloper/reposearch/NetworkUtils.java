package com.amrdeveloper.reposearch;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.amrdeveloper.reposearch.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by AmrDeveloper on 2/10/2018.
 */

public class NetworkUtils {

    private final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";

    private final static String PARAM_QUERY = "q";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    private final static String PARAM_SORT = "sort";
    private final static String SORT_BY = "stars";

    /**
     * Builds the URL used to query Github.
     *
     * @param gitHubSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the weather server.
     */
    private static URL buildUrl(String gitHubSearchQuery) {
        //Create Full Link
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, gitHubSearchQuery)
                .appendQueryParameter(PARAM_SORT, SORT_BY)
                .build();
        //Convert URI To URL
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }

    /**
     * This Method take Json String and formatter it
     * @param jsonResponse
     * @return list of repository to show it in listview
     */
    private static List<Repository> jsonFormatter(String jsonResponse){
        List<Repository> repositoryList = new ArrayList<>();
        try{
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.getJSONArray("items");
            //Get First 50 Repository
            int dataLen = 50;
            if(items.length() < dataLen){
                dataLen = items.length();
            }
            for(int i = 0 ; i < dataLen ; i++){
                JSONObject currentRepo = items.getJSONObject(i);
                String repoName = currentRepo.getString("name");
                String repoOwner = currentRepo.getJSONObject("owner").getString("login");
                String repoLang = currentRepo.getString("language");
                String repoStart = currentRepo.getString("stargazers_count");

                Log.v("Data","Number " + i);
                //Create Repository Object
                Repository repository = new Repository(repoName,repoOwner,repoLang,repoStart);
                //Add This Repository To List
                repositoryList.add(repository);
            }
        }
        catch (JSONException ex){
            Log.v("Network","Can't Read Json");
        }
        return repositoryList;
    }


    public static List<Repository> getDataFromApi(String query) throws IOException {
        //First Create URL
        URL apiURL = buildUrl(query);
        String jsonResponse = getResponseFromHttpUrl(apiURL);
        List<Repository> repositoryList = jsonFormatter(jsonResponse);
        return repositoryList;
    }


}
