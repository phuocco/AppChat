package com.example.appchat.Login;

import android.app.AlertDialog;
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

import com.example.appchat.Activity.BaseActivity;
import com.example.appchat.Activity.FirstTimeLoginActivity;
import com.example.appchat.Activity.MainActivity;
import com.example.appchat.Fragment.MessageFragment;
import com.example.appchat.Model.User;
import com.example.appchat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class SignInPasswordActivity extends BaseActivity {
    DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
    SharedPreferences sharedPreferences;
    ArrayList<User> listUser=new ArrayList<>();
    int check;
    EditText edt_PasswordSignIn;
    View v;
    String phoneNumber;
    User user;
    Button btn_Login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_sign_in);
        edt_PasswordSignIn=findViewById(R.id.edt_PasswordSignIn);
        btn_Login=findViewById(R.id.btn_Login);
        v=findViewById(R.id.PasswordSignInActivity);
        sharedPreferences=getSharedPreferences("PhoneNumberSignIn",MODE_PRIVATE);
       phoneNumber=sharedPreferences.getString("phoneNumberForSignIn","");
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        getListUser();
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(SignInPasswordActivity.this).build();
                alertDialog.setMessage(getString(R.string.processing));
                alertDialog.show();
                if(edt_PasswordSignIn.getText().toString().length()<5 && edt_PasswordSignIn.getText().toString().length()>0){
                    Snackbar.make(v,getString(R.string.passwordLessThan5),Snackbar.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else if(edt_PasswordSignIn.getText().toString().length()==0){
                    Snackbar.make(v,getString(R.string.notEnteredPassword),Snackbar.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else {
                    for (check = 0; check <  listUser.size(); check++) {
                        if (listUser.get(check).getMatKhau().equals(edt_PasswordSignIn.getText().toString()) && listUser.get(check).getPhoneNumber().equals(phoneNumber) && !(listUser.get(check).getName().isEmpty())) {
                            SharedPreferences.Editor editor=getSharedPreferences("SignIn",MODE_PRIVATE).edit();
                            editor.putString("Password",listUser.get(check).getMatKhau());
                            editor.apply();
                            Intent intent = new Intent(SignInPasswordActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignInPasswordActivity.this, listUser.get(check).getMatKhau(), Toast.LENGTH_SHORT).show();
                            finish();
                            alertDialog.dismiss();
                            break;
                        }
                        if (listUser.get(check).getMatKhau().equals(edt_PasswordSignIn.getText().toString()) && listUser.get(check).getPhoneNumber().equals(phoneNumber) && listUser.get(check).getName().isEmpty()) {
                            SharedPreferences.Editor editor=getSharedPreferences("KeyUser",MODE_PRIVATE).edit();
                            editor.putString("key",listUser.get(check).keyUser);
                            editor.putString("pass",edt_PasswordSignIn.getText().toString());
                            Log.e("password",edt_PasswordSignIn.getText().toString());
                            editor.apply();
                            alertDialog.dismiss();
                            Intent i =new Intent(SignInPasswordActivity.this, FirstTimeLoginActivity.class);
                                startActivity(i);
                                finish();
                        }
                        else if (!listUser.get(check).getMatKhau().equals(edt_PasswordSignIn.getText().toString()) && listUser.get(check).phoneNumber.equals(phoneNumber)) {
                            Snackbar.make(v, getString(R.string.passwordIncorrect), Snackbar.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            break;
                        }
                    }
                }
            }
        });


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
