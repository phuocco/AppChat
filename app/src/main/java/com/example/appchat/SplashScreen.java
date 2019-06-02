package com.example.appchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.appchat.Activity.MainActivity;
import com.example.appchat.Login.SignInPhoneNumberActivity;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SharedPreferences sharedPreferences=getSharedPreferences("PhoneNumberSignIn",MODE_PRIVATE);
                String phoneNumber=sharedPreferences.getString("phoneNumberForSignIn","");
                SharedPreferences sharedPreferences1=getSharedPreferences("SignIn",MODE_PRIVATE);
                String password=sharedPreferences1.getString("Password","");
                SharedPreferences sharedPreferences2=getSharedPreferences("KeyUser",MODE_PRIVATE);
                 String pass=sharedPreferences2.getString("pass","");
                Log.e("phone:",phoneNumber + ":"+password);
                if(phoneNumber.length()==10 && password.length()>=5 || phoneNumber.length()==10 && pass.length()>=5 ){
                    Intent intentToMain=new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intentToMain);
                    finish();
                }else {
                    Intent mainIntent = new Intent(SplashScreen.this, SignInPhoneNumberActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
