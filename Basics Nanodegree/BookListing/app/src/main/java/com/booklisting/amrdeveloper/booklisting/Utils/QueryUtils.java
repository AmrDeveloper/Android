package com.booklisting.amrdeveloper.booklisting.Utils;

import android.util.Log;

import com.booklisting.amrdeveloper.booklisting.Model.Book;

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
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class QueryUtils {

    /**
     * String tag for debugging
     */
    private static final String DEBUG_TAG = QueryUtils.class.getSimpleName();

    /**
     * @param link : The API link as String
     * @return : return link after convert it to URL Object
     */
    private static URL makeUrl(String link) {
        //Url object
        URL url = null;
        //Try To Convert
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            //Print Error Message if cant convert
            Log.v(DEBUG_TAG, "Cant Make Url");
        }
        //return url object
        return url;
    }

    /**
     * @param stream : InputStream Object from URL
     * @return : Reading InputStream and return result on String Object
     */
    private static String inputReader(InputStream stream) {
        //String Object to Save Json result
        StringBuilder jsonData = new StringBuilder();
        if (stream != null) {
            //Read Stream as Binary
            InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            //Convert This Stream To Chars
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            try {
                //read line by line
                String line = bufferedReader.readLine();
                //Read Until line equal null
                while (line != null) {
                    //Append This Line to Full Json Data
                    jsonData.append(line);
                    //Try To Read Next Line
                    line = bufferedReader.readLine();
                }
            } catch (IOException ex) {
                Log.v(DEBUG_TAG, "Cant Read This Stream");
            }
        }
        return jsonData.toString();
    }

    /**
     * @param link : API Link on URL Object to open connection with it
     * @return : return JSON data as String
     */
    private static String makeConnection(URL link) {
        String jsonResponse = "";
        if (link == null) {
            return jsonResponse;
        }
        InputStream inputStream = null;
        HttpsURLConnection urlConnection = null;
        try {
            //Open Connection
            urlConnection = (HttpsURLConnection) link.openConnection();
            //Server Request
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            //Check if connection is done or not
            if (urlConnection.getResponseCode() == 200) {
                //Get Input Stream
                inputStream = urlConnection.getInputStream();
                //Reading From Stream using My Method
                jsonResponse = inputReader(inputStream);
            }
        } catch (Exception e) {
            Log.v(DEBUG_TAG, "Connection Error");
        } finally {
            //On The End Close All Connection
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.v(DEBUG_TAG, "Cant Close Connection");
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    /**
     * @param jsonData : get Json data from api link and parsing it
     * @return : return information on List of Model Class Book
     */
    private static List<Book> dataParsing(String jsonData) {
        //Data List For Return It
        List<Book> dataList = new ArrayList<>();

        //Objects for every attribute in object
        String title = "";
        String imageUrl = "";
        String publisher = "";
        String categories = "";
        String description = "";

        //Try To Reading
        try {
            //Full Json String
            JSONObject data = new JSONObject(jsonData);
            //The Root Array
            JSONArray root = data.getJSONArray("items");
            //for every Object in Json Array
            for (int i = 0; i < root.length(); i++) {
                //Object index i
                JSONObject object = root.getJSONObject(i);
                //Book Info Object
                JSONObject volumeInfo = object.getJSONObject("volumeInfo");
                try {
                    //Title
                    try {
                        title = volumeInfo.getString("title");
                    } catch (Exception e) {
                        Log.v("Title", "No Title");
                    }
                    //Get Image url
                    try {
                        imageUrl = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                    } catch (Exception e) {
                        Log.v("Title", "No thumbnail");
                    }
                    //Get Publisher
                    try {
                        publisher = volumeInfo.getString("publisher");
                    } catch (Exception e) {
                        Log.v("Title", "No thumbnail");
                    }
                    //Get Description
                    try {
                        description = volumeInfo.getString("description");
                    } catch (Exception e) {
                        Log.v("Title", "No description");
                    }
                    //Get Categories
                    try {
                        categories = volumeInfo.getJSONArray("categories").get(i).toString();
                    } catch (Exception e) {
                        Log.v("Title", "No categories");
                    }
                    //Fill The Book
                    Book currentBook = new Book(title, imageUrl, publisher, categories, description);
                    //put This Book On List
                    dataList.add(currentBook);
                } catch (Exception e) {
                    Log.v(DEBUG_TAG, "Reading Not");
                }
            }
        } catch (JSONException ex) {
            Log.v(DEBUG_TAG, "Cant Parsing Data");
        }
        //return Data List
        return dataList;
    }

    /**
     * @param requestUrl : Controller Method to take API link as string
     *                   and using all class method
     * @return : return data after parsing in list for using in UI
     */
    public static List<Book> getDataFromInternet(String requestUrl) {
        //First Convert String to URL
        URL url = makeUrl(requestUrl);
        //Get JSON From API URL
        String json = makeConnection(url);
        //Parsing the Data and Return List Of Data
        return dataParsing(json);
    }
}
