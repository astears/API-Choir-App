package org.androidtown.choir;

import android.net.Uri;
import android.util.Log;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.net.MalformedURLException;

/**
 * Created by astears on 10/7/17.
 */

public class NetworkUtils {

    final static String LOGIN_BASE_URL = "https://choir-db.herokuapp.com/login.php";
    final static String SIGNUP_BASE_URL = "https://choir-db.herokuapp.com/signup.php";
    final static String USERNAME_PARAM = "username";
    final static String PASS_PARAM = "password";
    final static String FNAME_PARAM = "fName";
    final static String LNAME_PARAM = "lName";
    final static String EMAIL_PARAM = "email";
    final static String ROLE_PARAM = "role";

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
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
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildUrl(String username, String pass) {

        Uri builtUri = Uri.parse(LOGIN_BASE_URL).buildUpon()
                .appendQueryParameter(USERNAME_PARAM, username)
                .appendQueryParameter(PASS_PARAM, pass)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i("Built URI ", url.toString());

        return url;
    }

    public static URL buildUrl(SignupActivity.SignupInfo form) {

        Uri builtUri = Uri.parse(SIGNUP_BASE_URL).buildUpon()
                .appendQueryParameter(FNAME_PARAM, form.fName)
                .appendQueryParameter(LNAME_PARAM, form.lName)
                .appendQueryParameter(USERNAME_PARAM, form.username)
                .appendQueryParameter(PASS_PARAM, form.password)
                .appendQueryParameter(EMAIL_PARAM, form.email)
                .appendQueryParameter(ROLE_PARAM, "m")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i("Built URI ", url.toString());

        return url;
    }
}

