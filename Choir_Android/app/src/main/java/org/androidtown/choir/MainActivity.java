package org.androidtown.choir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String user_role = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginButtonPressed(View view) {

        EditText usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Make sure the user enters all fields
        if (username.matches("") || password.matches("")) {
            Toast.makeText(this, "Both username & password are required", Toast.LENGTH_SHORT).show();
        }
        else {
            new LoginTask().execute(username, password);
        }

    }

    public void signupButtonPressed(View view) {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    // Create a class that extends AsyncTask to perform network requests
    public class LoginTask extends AsyncTask<String, Void, String[]> {

        // Override the doInBackground method to perform your network requests
        @Override
        protected String[] doInBackground(String... params) {

            String username = params[0];
            String pass = params[1];
            URL loginRequestUrl = NetworkUtils.buildUrl(username, pass);

            try {
                String jsonLoginResponse = NetworkUtils
                        .getResponseFromHttpUrl(loginRequestUrl);

                JSONObject role = new JSONObject(jsonLoginResponse);
                String[] simpleJsonLoginData = new String[1];
                simpleJsonLoginData[0] = role.getString("role");

                return simpleJsonLoginData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // Override the onPostExecute method to display the results of the network request
        @Override
        protected void onPostExecute(String[] loginData) {
            user_role = loginData[0];
            if (user_role != null) {

                if (user_role.equals("m")) {
                    Intent intent = new Intent(MainActivity.this, MemberActivity.class);
                    startActivity(intent);
                }
                else if (user_role.equals("d")) {
                    Intent intent = new Intent(MainActivity.this, DirectorActivity.class);
                    startActivity(intent);
                }
                else if (user_role.equals("Incorrect Username")) {
                    Toast.makeText(MainActivity.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                }
                else if (user_role.equals("Incorrect Password")) {
                    Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
