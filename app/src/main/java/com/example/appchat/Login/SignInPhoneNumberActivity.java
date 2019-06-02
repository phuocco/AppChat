package com.example.appchat.Login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appchat.Activity.BaseActivity;
import com.example.appchat.Activity.FirstTimeLoginActivity;
import com.example.appchat.Model.User;
import com.example.appchat.ProfileActivity;
import com.example.appchat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class SignInPhoneNumberActivity extends BaseActivity {
    DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
    EditText edt_PhoneNumberSignIn;
    ArrayList<String> listPhone=new ArrayList<>();
    View v;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_sign_in);
        edt_PhoneNumberSignIn=findViewById(R.id.edt_PhoneNumberSignIn);
        v=findViewById(R.id.phoneNumberForSignInActivity);
        checkInternetConnect();
        if(!checkInternetConnect()){
            final AlertDialog.Builder builder = new AlertDialog.Builder(SignInPhoneNumberActivity.this);
            builder.setPositiveButton("Turn on Wi-fi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                }
            });
            builder.setNegativeButton("Mobile Internet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("com.android.settings",
                            "com.android.settings.Settings$DataUsageSummaryActivity"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            builder.setTitle("Oops!");
            builder.setMessage("You aren't connected to the Internet ");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        getPhoneNumber();




    }

    public void goToSignUp(View view){
        if(!checkInternetConnect()){
            Snackbar.make(v,getString(R.string.notConnectInternet),Snackbar.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(SignInPhoneNumberActivity.this, SignUpActivity.class);
            startActivity(i);
    }
    }

    public void goToInsertPassword(View view) {
        final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(SignInPhoneNumberActivity.this).build();
        alertDialog.setMessage(getString(R.string.processing));
        alertDialog.show();
        if(!checkInternetConnect()){
            Snackbar.make(v,getString(R.string.notConnectInternet),Snackbar.LENGTH_SHORT).show();
            alertDialog.dismiss();
        }

            if (edt_PhoneNumberSignIn.getText().toString().length() < 10 && edt_PhoneNumberSignIn.getText().toString().length() > 0 || edt_PhoneNumberSignIn.getText().toString().length() > 10) {
                Snackbar.make(v, getString(R.string.tryAgain), Snackbar.LENGTH_LONG).show();
                edt_PhoneNumberSignIn.setText("");
                alertDialog.dismiss();
            }
            if (listPhone.contains(edt_PhoneNumberSignIn.getText().toString()) && checkInternetConnect()) {
                SharedPreferences.Editor editor = getSharedPreferences("PhoneNumberSignIn", MODE_PRIVATE).edit();
                editor.putString("phoneNumberForSignIn", edt_PhoneNumberSignIn.getText().toString());
                editor.apply();
                Intent goToSignInPassword = new Intent(SignInPhoneNumberActivity.this, SignInPasswordActivity.class);
                startActivity(goToSignInPassword);
                alertDialog.dismiss();
            }
            if (!(listPhone.contains(edt_PhoneNumberSignIn.getText().toString())) && edt_PhoneNumberSignIn.getText().toString().length() > 0) {
                Snackbar.make(v, getString(R.string.phoneNumberNotRegistered), Snackbar.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
            if (edt_PhoneNumberSignIn.getText().toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.phoneNumberEmpty), Snackbar.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }



    }
    public void getPhoneNumber(){
        mData.child("User").orderByChild("phoneNumber").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                user=new User();
                user=dataSnapshot.getValue(User.class);
                assert user != null;
                listPhone.add(user.getPhoneNumber());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    private boolean checkInternetConnection(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//
//    }


}
