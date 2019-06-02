package com.example.appchat.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchat.Activity.FirstTimeLoginActivity;
import com.example.appchat.Activity.MainActivity;
import com.example.appchat.Login.SignInPhoneNumberActivity;
import com.example.appchat.Model.User;
import com.example.appchat.R;
import com.github.chrisbanes.photoview.PhotoView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    TextView tv_phoneNumber,tv_name;
    DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
    Button btn_Logout,change_Avatar;
    User user;
    AlertDialog mDialog;
    String phoneNumber,key,pass,name;
    ImageView avatar,imv_dialog;
    int i;
    ArrayList<User> listUser=new ArrayList<>();
    SharedPreferences sharedPreferences;
    String linkAvatar,avatarUpdate;
    PhotoView photoView;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://appchat-e7dcb.appspot.com");
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        getListUser();
         sharedPreferences=getActivity().getSharedPreferences("PhoneNumberSignIn",MODE_PRIVATE);
         phoneNumber=sharedPreferences.getString("phoneNumberForSignIn","");
        tv_phoneNumber=v.findViewById(R.id.tv_phoneNumber);
        tv_name=v.findViewById(R.id.tv_name);
        avatar=v.findViewById(R.id.avatar);
        tv_phoneNumber.setText(phoneNumber+" ");
        btn_Logout=v.findViewById(R.id.btn_Logout);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.view_full_avatar, null);
                photoView = mView.findViewById(R.id.imv_ViewFullAvatar);
                change_Avatar=mView.findViewById(R.id.btn_changeAvatar);
                Picasso.get().load(linkAvatar).into(photoView);
                mBuilder.setView(mView);
                mDialog = mBuilder.create();
                mDialog.show();
                change_Avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Crop.pickImage(getActivity(),ProfileFragment.this);
                    }
                });
            }
        });




        //Logout
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor =getActivity().getSharedPreferences("PhoneNumberSignIn",MODE_PRIVATE).edit();
                editor.putString("phoneNumberForSignIn","");
                editor.apply();
                SharedPreferences.Editor editor1=getActivity().getSharedPreferences("SignIn",MODE_PRIVATE).edit();
                editor1.putString("Password","");
                editor1.apply();
                Intent intentToLogin=new Intent(getActivity(), SignInPhoneNumberActivity.class);
                startActivity(intentToLogin);
                getActivity().finish();
            }
        });


        return v;
    }
    private void getListUser(){
        mData.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                user=new User();
                user=dataSnapshot.getValue(User.class);
                user.setKeyUser(dataSnapshot.getKey());
                listUser.add(user);
                //Set name, set Avatar
                for(i=0;i<listUser.size();i++){
                    if(listUser.get(i).phoneNumber.contains(phoneNumber)){
                        tv_name.setText(listUser.get(i).name);
                        Picasso.get().load(listUser.get(i).getAvatar()).into(avatar);
                        linkAvatar=listUser.get(i).avatar;
                        key=listUser.get(i).getKeyUser();
                        Log.e("key",key);
                        pass=listUser.get(i).matKhau;
                        name=listUser.get(i).getName();
                    }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == Crop.REQUEST_PICK){
                Uri source_uri=data.getData();
                Uri destination_uri=Uri.fromFile(new File(getActivity().getCacheDir(),"cropped"));
                Crop.of(source_uri,destination_uri).asSquare().start(getActivity(), ProfileFragment.this);
                photoView.setImageURI(Crop.getOutput(data));
                Log.e("aaa","abc");
                change_Avatar.setText("Apply");
                change_Avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateAvtar();
                        avatar.setImageURI(Crop.getOutput(data));

                    }
                });

            }
            else  if(requestCode == Crop.REQUEST_CROP){
                handle_crop(resultCode,data);
            }
        }

    }

    private void handle_crop(int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            photoView.setImageURI(Crop.getOutput(data));
        }
    }
    private void updateAvtar(){
        final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(getActivity()).build();
        alertDialog.setMessage(getString(R.string.processing));
        alertDialog.show();
        Calendar calendar = Calendar.getInstance();
        final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
        photoView.setDrawingCacheEnabled(true);
        photoView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) photoView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        final UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

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
                            avatarUpdate = downloadUri.toString();
                            Log.e("key",key);
                             User userUpdate=new User(phoneNumber,name,pass,avatarUpdate);
                                mData.child("User").child(key).setValue(userUpdate, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@com.google.firebase.database.annotations.Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        if (databaseError == null) {

                                            alertDialog.dismiss();
                                            mDialog.dismiss();

                                        } else {
                                            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
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




