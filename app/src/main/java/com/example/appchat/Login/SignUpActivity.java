package com.example.appchat.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appchat.Model.User;
import com.example.appchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    EditText edt_PhoneNumber;
    SharedPreferences sharedPreferences;
    DatabaseReference mData=FirebaseDatabase.getInstance().getReference();
    ArrayList<String> listUser=new ArrayList<>();
    User user;
    View view;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edt_PhoneNumber = findViewById(R.id.edt_PhoneNumberSignUp);
        view = findViewById(R.id.layoutSignUpPhoneNumber);
        getListPhone();

    }
    public void goToInsertPasswordForSignUp(View view){
        if(edt_PhoneNumber.getText().toString().length()<10 || edt_PhoneNumber.getText().toString().length()>10 || edt_PhoneNumber.getText().toString().isEmpty()){
            Snackbar.make(view,"Try again",Snackbar.LENGTH_SHORT).show();
        }
        if(listUser.contains(edt_PhoneNumber.getText().toString())){
            Snackbar.make(view,getString(R.string.phoneNumberUsed),Snackbar.LENGTH_SHORT).show();
        }
        if(!(listUser.contains(edt_PhoneNumber.getText().toString())) && !(edt_PhoneNumber.getText().toString().isEmpty())){
            SharedPreferences.Editor editor=getSharedPreferences("PhoneNumberSignUp",MODE_PRIVATE).edit();
            editor.putString("phoneNumberForSignUp",edt_PhoneNumber.getText().toString());
            editor.apply();
            Intent goToInsertPassword=new Intent(SignUpActivity.this,SignUpPasswordActivity.class);
            startActivity(goToInsertPassword);

        }

    }


    public void goToSignIn(View view) {
        startActivity(new Intent(this, SignInPhoneNumberActivity.class));
    }
    public  void getListPhone(){
        mData.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                user=new User();
                user=dataSnapshot.getValue(User.class);
                assert user != null;
                listUser.add(user.getPhoneNumber());

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
