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
        String confirmPassword;
        String email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSignupSubmit(View view) {

        EditText _firstName = (EditText)findViewById(R.id.firstName);
        EditText _lastName = (EditText)findViewById(R.id.lastName);
        EditText _username = (EditText)findViewById(R.id.signupUsername);
        EditText _password = (EditText)findViewById(R.id.signupPassword);
        EditText _confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        EditText _email = (EditText)findViewById(R.id.email);

        String firstName = _firstName.getText().toString();
        String lastName= _lastName.getText().toString();
        String username = _username.getText().toString();
        String password = _password.getText().toString();
        String confirmPassword = _confirmPassword.getText().toString();
        String email = _email.getText().toString();

        if (firstName.matches("") || lastName.matches("") || username.matches("") || password.matches("") ||
                confirmPassword.matches("") || email.matches("")) {
            Toast.makeText(this, "Please fill in all fields to signup", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password fields don't match", Toast.LENGTH_SHORT).show();
        }
        else {
            new SignupTask().execute(firstName, lastName, username, password, email);
        }

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

            URL signupRequestUrl = NetworkUtils.buildUrl(signupForm);

            try {
                String jsonSignupResponse = NetworkUtils
                        .getResponseFromHttpUrl(signupRequestUrl);

                JSONObject role = new JSONObject(jsonSignupResponse);
                String[] simpleJsonSignupData = new String[1];
                simpleJsonSignupData[0] = role.getString("result");

                return simpleJsonSignupData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // Override the onPostExecute method to display the results of the network request
        @Override
        protected void onPostExecute(String[] loginData) {
            String queryResult = loginData[0];
            if (queryResult != null) {
                if (queryResult.equals("Username exists")) {
                    Toast.makeText(SignupActivity.this, "Username is unavailable", Toast.LENGTH_SHORT).show();
                }
                else if (queryResult.equals("Email registered")) {
                    Toast.makeText(SignupActivity.this, "An account is already registered with this email",
                            Toast.LENGTH_SHORT).show();
                }
                else if (queryResult.equals("true")) {
                    Intent intent = new Intent(SignupActivity.this, MemberActivity.class);
                    startActivity(intent);
                }
                else if (queryResult.equals("false")) {
                    Toast.makeText(SignupActivity.this, "An error occured creating an account",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
