package com.example.venetatodorova.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        WebView webView = (WebView) findViewById(R.id.webView);

        if(savedInstanceState != null){
            html = (String) savedInstanceState.get("Data");
            webView.loadUrl("file:///storage/emulated/0/Download/"+html);
        }

        Intent intent = getIntent();
        html = intent.getStringExtra("html");

        webView.loadUrl("file:///storage/emulated/0/Download/"+html);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("Data",html);
        super.onSaveInstanceState(outState);
    }
}
