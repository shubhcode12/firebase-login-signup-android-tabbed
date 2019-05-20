package com.smtech.firebase.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.smtech.firebase.R;

public class HomeFragment extends Fragment {

    WebView mywebview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        mywebview = (WebView)v.findViewById(R.id.webView);
        WebSettings webSettings = mywebview.getSettings();
        mywebview.loadUrl("https://uxfree.com/");
        mywebview.setWebViewClient(new WebViewClient());
        return v;

    }

}
