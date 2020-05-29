package com.smtech.firebase.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.smtech.firebase.R;

public class HomeFragment extends Fragment {

    private CardView cardView;
    private WebView webView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        //cardView = (CardView)v.findViewById(R.id.cardview);






        return v;

    }

}
