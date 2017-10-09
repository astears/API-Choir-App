package org.androidtown.choir;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    public class SignupInfo {
        String fName;
        String lName;
        String username;
        String password;
        String email;
        String phone;
        String role;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSignupSubmit(View view) {

        String firstName = findViewById(R.id.firstName).toString();
        String lastName = findViewById(R.id.lastName).toString();
        String username = findViewById(R.id.signupUsername).toString();
        String password = findViewById(R.id.signupPassword).toString();
        String confirmPassword = findViewById(R.id.confirmPassword).toString();
        String email = findViewById(R.id.email).toString();

    }

    public class SignupTask extends AsyncTask<String, Void, String[]> {

        // Override the doInBackground method to perform your network requests
        @Override
        protected String[] doInBackground(String... params) {

            SignupInfo signupForm = new SignupInfo();
            signupForm.fName = params[0];
            signupForm.lName = params[1];
            signupForm.username = params[2];
            signupForm.password = params[3];
            signupForm.email = params[4];
            signupForm.phone = params[5];
            signupForm.role = params[6];

            URL loginRequestUrl = NetworkUtils.buildUrl(signupForm);

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

        }
    }
}
