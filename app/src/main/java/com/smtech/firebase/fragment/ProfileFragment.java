package com.smtech.firebase.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.smtech.firebase.R;



public class ProfileFragment extends Fragment  {

    private FirebaseUser user;
    private Button signOut;
    private TextView textEmail;
    private FirebaseAuth mAuth;



    public static final String TAG = "LOGIN";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getInstance().getCurrentUser();
        textEmail = (TextView) View.findViewById(R.id.Profile_mail);

        signOut = (Button) View.findViewById(R.id.btn_signOut);




        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        textEmail.setText(user.getEmail());



        return View;


    }


}