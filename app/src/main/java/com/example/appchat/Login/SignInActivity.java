package com.example.appchat.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.appchat.ProfileActivity;
import com.example.appchat.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void goToSignUp(View view){
            Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(i);
    }

    public void goToProfile(View view) {
        startActivity(new Intent(SignInActivity.this, ProfileActivity.class));
    }


}
