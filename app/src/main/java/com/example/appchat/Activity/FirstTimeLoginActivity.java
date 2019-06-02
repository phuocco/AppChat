package com.example.appchat.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.lang.UScript;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appchat.Model.User;
import com.example.appchat.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class FirstTimeLoginActivity extends AppCompatActivity {
    EditText edt_UserName;
    Button btn_Save;
    SharedPreferences keyUser,phoneNumber;
    DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
    ImageView imv_Avatar;
    User userUpdate;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://appchat-e7dcb.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_login);
        edt_UserName=findViewById(R.id.edt_UserName);
        imv_Avatar=findViewById(R.id.imv_Avatar);
        keyUser=getSharedPreferences("KeyUser",MODE_PRIVATE);
        phoneNumber=getSharedPreferences("PhoneNumberSignIn",MODE_PRIVATE);
        btn_Save=findViewById(R.id.btn_Save);
        imv_Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(FirstTimeLoginActivity.this);


            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String key= keyUser.getString("key","");
//                String phone=phoneNumber.getString("phoneNumberForSignIn","");
//                String pass=keyUser.getString("pass","");
//                Log.e("pass",pass);
//                String name= edt_UserName.getText().toString();
//                userUpdate=new User(phone,name,pass,"");
//                Log.e("key",key);
                //get avatar and push to Storage
                final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(FirstTimeLoginActivity.this).build();
                alertDialog.setMessage(getString(R.string.processing));
                alertDialog.show();
                if(edt_UserName.getText().toString().isEmpty()){
                    Toast.makeText(FirstTimeLoginActivity.this, getString(R.string.enterYourName), Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
                    imv_Avatar.setDrawingCacheEnabled(true);
                    imv_Avatar.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imv_Avatar.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    final UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(FirstTimeLoginActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            imv_Avatar.setImageResource(R.drawable.ic_launcher_foreground);
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return mountainsRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        String key = keyUser.getString("key", "");
                                        String phone = phoneNumber.getString("phoneNumberForSignIn", "");
                                        String pass = keyUser.getString("pass", "");
                                        Log.e("pass", pass);
                                        String name = edt_UserName.getText().toString();
                                        Log.e("key", key);
                                        String avatar = downloadUri.toString();
                                        userUpdate = new User(phone, name, pass, avatar);
                                        mData.child("User").child(key).setValue(userUpdate, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                if (databaseError == null) {
                                                    alertDialog.dismiss();
                                                    Intent intent = new Intent(FirstTimeLoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();

                                                } else {
                                                    Toast.makeText(FirstTimeLoginActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });


                                        Log.d("Link", downloadUri + "");
                                    } else {
                                        // Handle failures
                                        // ...
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == Crop.REQUEST_PICK){
                Uri source_uri=data.getData();
                Uri destination_uri=Uri.fromFile(new File(getCacheDir(),"cropped"));
                Crop.of(source_uri,destination_uri).asSquare().start(this);
                imv_Avatar.setImageURI(Crop.getOutput(data));
            }
            else  if(requestCode == Crop.REQUEST_CROP){
                handle_crop(resultCode,data);
            }
        }

    }

    private void handle_crop(int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            imv_Avatar.setImageURI(Crop.getOutput(data));
        }
    }
}
