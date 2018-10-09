package com.marwa.eltayeb.tweetme;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class NetworkUtils {
    //Tag For Debugging
    private final static String LOG_TAG = "NETWORK";

    /**
     * @param apiString The Api Link as String
     * @return Return api link as URL objects
     */
    private static URL createApiUrl(String apiString) {
        URL url = null;
        try {
            url = new URL(apiString);
        } catch (IOException ex) {
            Log.v(LOG_TAG, "Can't Create URL");
        }
        return url;
    }

    /**
     * @param link to open connection with it
     * @return return Json from api
     */
    private static void makeConnection(URL link) {
        HttpURLConnection urlConnection = null;
        try {
            //Open Connection
            urlConnection = (HttpURLConnection) link.openConnection();
            //Server Request
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //On The End Close All Connection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }


    //Public Method to Using With Class To Control all Class
    public static void readDataFromApi(String requestApi) {
        //From String to Url
        URL apiUrl = createApiUrl(requestApi);
        //Get Data And Save It On Stream
        makeConnection(apiUrl);
    }
}
