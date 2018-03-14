package org.androidtown.choir;

/**
 * Created by astears on 3/13/18.
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import org.w3c.dom.Text;

public class ResetPassword extends AppCompatActivity {

    RelativeLayout rl1, rl2, rl3;
    TextView tv1, tv2, tv3;
    Button btn1;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        //Relative Layouts
        rl1 = findViewById(R.id.rl_one_fp);
        rl2 = findViewById(R.id.rl_two_fp);

        //Text View's
        tv1 = findViewById(R.id.tv_one_fp);
        tv2 = findViewById(R.id.tv_two_fp);
        tv3 = findViewById(R.id.tv_three_fp);

        //Edit Text
        et1 = findViewById(R.id.et_one_fp);

        //button
        btn1 = findViewById(R.id.btn_one_fp);
    }

    public void onForgotPassClick(View view) {

        final String email = et1.getText().toString();

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();

                    } catch(Exception e) {
                        Log.e("FireBase Error", e.getMessage());
                    }
                }
                else if (task.getResult().getProviders().size() > 0) {

                    sendResetLink(email, auth);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Email not registered", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void sendResetLink(String emailAddress, FirebaseAuth auth) {

        if (emailAddress != null) {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "Email sent.");
                                Toast.makeText(getApplicationContext(), "Recovery link sent to your email!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "An error occurred, please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}

