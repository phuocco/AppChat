package com.example.appchat.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appchat.MainActivity;
import com.example.appchat.Model.User;
import com.example.appchat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignInPasswordActivity extends AppCompatActivity {
    DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
    SharedPreferences sharedPreferences;
    ArrayList<User> listUser=new ArrayList<>();
    int check;
    EditText edt_PasswordSignIn;
    View v;
    String phoneNumber;
    User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_sign_in);
        edt_PasswordSignIn=findViewById(R.id.edt_PasswordSignIn);

        v=findViewById(R.id.PasswordSignInActivity);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
       phoneNumber=sharedPreferences.getString("phoneNumberForSignIn","");
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        getListUser();


    }
    protected void goToMain(View view){
        if(edt_PasswordSignIn.getText().toString().length()<5 && edt_PasswordSignIn.getText().toString().length()>0){
            Snackbar.make(v,getString(R.string.passwordLessThan5),Snackbar.LENGTH_SHORT).show();
        }else if(edt_PasswordSignIn.getText().toString().length()==0){
                Snackbar.make(v,getString(R.string.notEnteredPassword),Snackbar.LENGTH_SHORT).show();
        }else {
            for (check = 0; check < listUser.size(); check++) {
                if (listUser.get(check).getMatKhau().equals(edt_PasswordSignIn.getText().toString()) && listUser.get(check).getPhoneNumber().equals(phoneNumber)) {
                    Intent intent = new Intent(SignInPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                } else if (!listUser.get(check).matKhau.equals(edt_PasswordSignIn.getText().toString()) && listUser.get(check).phoneNumber.equals(phoneNumber)) {
                    Snackbar.make(v, "Failed", Snackbar.LENGTH_SHORT).show();
                    break;
                }
            }
        }

    }
    protected void getListUser(){
        mData.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                user=dataSnapshot.getValue(User.class);
                user.setKeyUser(dataSnapshot.getKey());

                listUser.add(user);


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
