package com.example.appchat.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchat.Activity.FirstTimeLoginActivity;
import com.example.appchat.Model.User;
import com.example.appchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    DatabaseReference mData=FirebaseDatabase.getInstance().getReference();
    User user,userUpdate;
    ArrayList<User> listUser1=new ArrayList<>();
    SharedPreferences sharedPreferences,sharedPassword;
    Context c;
    int i;
    View viewSnackbar;
    Dialog dialog;
    EditText enterUserName;
    Button btn_Accept;
    TextView test;
    public MessageFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_message, container, false);
        viewSnackbar=v.findViewById(R.id.viewMessage);
        Log.e("create","createview");
//        getListUser();
        test=v.findViewById(R.id.test);
        sharedPreferences=getActivity().getSharedPreferences("PhoneNumberSignIn", Context.MODE_PRIVATE);
        final String phoneNumber=sharedPreferences.getString("phoneNumberForSignIn","");
        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dailog_insert_user_name);
        enterUserName=dialog.findViewById(R.id.enterUsername);
        btn_Accept=dialog.findViewById(R.id.btn_accept);


        return v;
    }





}
