package com.example.appchat.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appchat.Model.User;
import com.example.appchat.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPasswordActivity extends AppCompatActivity {
    EditText edt_PasswordSignUp,edt_REPasswordSignUp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_password);
        connectView();
    }
    protected void connectView(){
        edt_PasswordSignUp=findViewById(R.id.edt_PasswordSignUp);
        edt_REPasswordSignUp=findViewById(R.id.edt_REPasswordSignUp);

    }
    protected void SignUp(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("PhoneNumberSignUp", MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phoneNumberForSignUp", "");
        String name="";
        String avatar="";
        String password=edt_PasswordSignUp.getText().toString();
        String Re_Password=edt_REPasswordSignUp.getText().toString();
        if(password.length()<5 || Re_Password.length()<5){
            Toast.makeText(this, getString(R.string.passwordLessThan5), Toast.LENGTH_SHORT).show();
        }
        if (!password.matches(Re_Password)){
            Toast.makeText(this, getString(R.string.RePasswordNotMatch), Toast.LENGTH_SHORT).show();
        }
        if(password.matches(Re_Password)&&password.length()<5 || password.matches(Re_Password)&&Re_Password.length()<5){
            Toast.makeText(this, getString(R.string.passwordLessThan5), Toast.LENGTH_SHORT).show();
        }
        if(password.matches(Re_Password)){
//            SharedPreferences.Editor editor= (SharedPreferences.Editor) getSharedPreferences("User",MODE_PRIVATE);
//            editor.remove("phoneNumberForSignUp");
//            editor.apply();
            User user=new User(phoneNumber,name,password,avatar);
            mData.child("User").push().setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Toast.makeText(SignUpPasswordActivity.this, getString(R.string.Success), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpPasswordActivity.this, getString(R.string.Failed), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Intent intentToSignIn=new Intent(this,SignInPhoneNumberActivity.class);
            startActivity(intentToSignIn);
            finish();

        }

    }
}
