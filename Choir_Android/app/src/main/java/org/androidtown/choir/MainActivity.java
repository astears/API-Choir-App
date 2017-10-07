package org.androidtown.choir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginButtonPressed(View view) {

        EditText usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        EditText roleEditText = (EditText)findViewById(R.id.roleEditText);

        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("") ||
        roleEditText.getText().toString().matches("")) {
            Toast.makeText(this, "Both username, password, and role are required", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Username", usernameEditText.getText().toString());
            Log.i("Password", passwordEditText.getText().toString());
            Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
