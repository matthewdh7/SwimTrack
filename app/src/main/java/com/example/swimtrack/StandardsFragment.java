package com.example.swimtrack;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by hungmat20 on 3/6/2019.
 */

public class StandardsFragment extends Fragment implements MainActivity.OnBackPressedListener {
    private WebView webView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_standards, container, false);

        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        webView = (WebView) v.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setTextZoom(200);

        String appCachePath = getActivity().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAppCacheEnabled(true);

        if (!isNetworkAvailable()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }

        final String googleDocs = "http://drive.google.com/viewerng/viewer?embedded=true&url=";

        Button buttonGold = v.findViewById(R.id.button_gold);
        buttonGold.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webView.loadUrl(googleDocs + "https://www.teamunify.com/pnws2/UserFiles/File/TimeStandards/2018-19_Gold_Silver_Times.pdf");
            }
        });

        Button button14u= v.findViewById(R.id.button_14u);
        button14u.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webView.loadUrl(googleDocs + "https://www.teamunify.com/pnws2/UserFiles/File/TimeStandards/2018_14U_SC_Standards.pdf");
            }
        });

        Button buttonSenior = v.findViewById(R.id.button_senior);
        buttonSenior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webView.loadUrl(googleDocs + "https://www.teamunify.com/pnws2/UserFiles/File/TimeStandards/2018_Washington_State_Sr_Champs-1.pdf");
            }
        });

        Button buttonRegionals = v.findViewById(R.id.button_regionals);
        buttonRegionals.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webView.loadUrl(googleDocs + "https://www.teamunify.com/pnws2/UserFiles/File/TimeStandards/2019-nwag-standards-1_093594.pdf");
            }
        });

        Button buttonZoneAge = v.findViewById(R.id.button_zone_age);
        buttonZoneAge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webView.loadUrl(googleDocs + "https://www.teamunify.com/pnws2/UserFiles/File/TimeStandards/2019%20WZ%20AG%20Time%20Standards.pdf");
            }
        });

        Button buttonZoneSenior = v.findViewById(R.id.button_zone_senior);
        buttonZoneSenior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webView.loadUrl(googleDocs + "https://www.teamunify.com/pnws2/UserFiles/File/TimeStandards/2019_WZ_Sr_Zones_TS.pdf");
            }
        });

        return v;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void doBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }
}
