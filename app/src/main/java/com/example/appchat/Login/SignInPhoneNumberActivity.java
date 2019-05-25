package com.example.appchat.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appchat.Model.User;
import com.example.appchat.ProfileActivity;
import com.example.appchat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignInPhoneNumberActivity extends AppCompatActivity {
    DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
    EditText edt_PhoneNumberSignIn;
    View v;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_sign_in);
        edt_PhoneNumberSignIn=findViewById(R.id.edt_PhoneNumberSignIn);
        v=findViewById(R.id.phoneNumberForSignInActivity);
    }

    public void goToSignUp(View view){
            Intent i = new Intent(SignInPhoneNumberActivity.this, SignUpActivity.class);
            startActivity(i);
    }

    public void goToInsertPassword(View view) {

        if(edt_PhoneNumberSignIn.getText().toString().length()<10 || edt_PhoneNumberSignIn.getText().toString().length()>10){
            Snackbar.make(v,getString(R.string.tryAgain),Snackbar.LENGTH_LONG).show();
            edt_PhoneNumberSignIn.setText("");
        }else {
            mData.child("User").orderByChild("phoneNumber").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    user=dataSnapshot.getValue(User.class);
                    if(user.getPhoneNumber().equals(edt_PhoneNumberSignIn.getText().toString())){
                        SharedPreferences.Editor editor = getSharedPreferences("User", MODE_PRIVATE).edit();
                        editor.putString("phoneNumberForSignIn", edt_PhoneNumberSignIn.getText().toString());
                        editor.apply();
                        Intent goToInsertPasswordForSignIn = new Intent(SignInPhoneNumberActivity.this, SignInPasswordActivity.class);
                        startActivity(goToInsertPasswordForSignIn);
                    }
                    if(!user.getPhoneNumber().equals(edt_PhoneNumberSignIn)){
                        Snackbar.make(v,getString(R.string.phoneNumberNotRegistered),Snackbar.LENGTH_SHORT).show();
                    }
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

    }


}
