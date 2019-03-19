package com.example.swimtrack;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by hungmat20 on 3/6/2019.
 */

public class ResultsFragment extends Fragment implements MainActivity.OnBackPressedListener {
    public static final int ADD_TIME_REQUEST = 1;
    private TimeViewModel timeViewModel;
    private WebView webView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results, container, false);

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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementById('column_side').style.visibility = 'hidden'; " +
                        "document.getElementById('column_side').style.width = '11.5%'; " +
                        "document.getElementById('main_menu').style.display='none'; " +
                        "document.getElementById('header').style.display='none'; " +
                        "document.getElementsByClassName('tabs')[0].style.display='none'; " +
                        "document.getElementById('utilities').style.display='none'; " +
                        "})()");
            }
        });

        webView.loadUrl("https://www.teamunify.com/EventsPast.jsp?_tabid_=2434&team=pnws2");


        FloatingActionButton buttonAddTime = v.findViewById(R.id.button_add_time);
        buttonAddTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTimeActivity.class);
                startActivityForResult(intent, ADD_TIME_REQUEST);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TIME_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddTimeActivity.EXTRA_NAME);
            String time = data.getStringExtra(AddTimeActivity.EXTRA_TIME);
            String date = data.getStringExtra(AddTimeActivity.EXTRA_DATE);
            boolean bestTime = data.getBooleanExtra(AddTimeActivity.EXTRA_BESTTIME, false);

            Time newTime = new Time(name, time, date, bestTime);
            timeViewModel = ViewModelProviders.of(this).get(TimeViewModel.class);
            timeViewModel.insert(newTime);

            Toast.makeText(getContext(), "New time entered", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "New time not entered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }
}
