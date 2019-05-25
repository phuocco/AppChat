package com.example.appchat.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    ArrayList<User> listUser=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        connectView();
    }
    protected void connectView(){
        edt_PhoneNumber=findViewById(R.id.edt_PhoneNumberSignUp);
    }
    public void goToSignIn(View view) {
        startActivity(new Intent(this, SignInPhoneNumberActivity.class));
    }
    protected void goToInsertPasswordForSignUp(View view){
        if(edt_PhoneNumber.getText().toString().length()<10 || edt_PhoneNumber.getText().toString().length()>10){
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
            edt_PhoneNumber.setText("");
        }else {
            mData.child("User").orderByChild("phoneNumber").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    User user=dataSnapshot.getValue(User.class);
                    listUser.add(user);
                    if(user.getPhoneNumber().equals(edt_PhoneNumber.getText().toString())){
                        Toast.makeText(SignUpActivity.this, getString(R.string.phoneNumberUsed), Toast.LENGTH_SHORT).show();
                    }else {
                        SharedPreferences.Editor editor = getSharedPreferences("User", MODE_PRIVATE).edit();
                        editor.putString("phoneNumberForSignUp", edt_PhoneNumber.getText().toString());
                        editor.apply();
                        Intent goToInsertPasswordForSignUp = new Intent(SignUpActivity.this, SignUpPasswordActivity.class);
                        startActivity(goToInsertPasswordForSignUp);
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
