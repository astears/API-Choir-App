package org.androidtown.choir;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.net.URL;

import static junit.runner.BaseTestRunner.savePreferences;

public class MainActivity extends AppCompatActivity {

    private String user_role = null;
    EditText usernameEditText;
    EditText passwordEditText;

    SessionManager session;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            //already signed in
            Intent i = new Intent(this, MemberActivity.class);
            startActivity(i);
        }

//        session = new SessionManager(getApplicationContext());
//        session.checkLogin();

    }

    public void onLoginClick(View view) {

        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Make sure the user enters all fields
        if (username.matches("") || password.matches("")) {
            Toast.makeText(this, "Both username & password are required", Toast.LENGTH_SHORT).show();
        }
        else {
            //new LoginTask().execute(username, password);
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(MainActivity.this, MemberActivity.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Please provide a valid email and password",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }

    }

    public void onForgotPasswordClick(View view) {
        Intent intent = new Intent(MainActivity.this, ResetPassword.class);
        startActivity(intent);
    }

    public void onRegisterClick(View view) {
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
                    session.createLoginSession();
                    Intent intent = new Intent(MainActivity.this, MemberActivity.class);
                    startActivity(intent);
                }
                else if (user_role.equals("d")) {
                    session.createLoginSession();
                    Intent intent = new Intent(MainActivity.this, MemberActivity.class);
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

    @Override
    protected void onNewIntent(Intent intent){
        int i = intent.getIntExtra("FLAG", 0);

        if(i == 0)
            finish();

    }
}
