package com.example.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity {
    WebView webView;

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        getSupportActionBar().hide();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.javatpoint.com/contact-us");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Browser_Home());
        findViewById(R.id.home_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("https://www.javatpoint.com/contact-us");
            }
        });
    }
    private class Browser_Home extends WebViewClient {
        Browser_Home(){}
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            MainActivity.this.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // below code is to click on a html element
//                    view.loadUrl("javascript:(function() { " +
//                            "var element = document.getElementsByClassName('nav_tab')[1]; " +
//                            "if (element) { " +
//                            "element.click(); " +
//                            "} " +
//                            "})()");
                    view.loadUrl("javascript:(function() { " +
                            "var element = document.getElementById('discuss-tab').firstChild; " +
                            "if (element) { " +
                            "element.click(); " +
                            "} " +
                            "})()");
                    // below code is to find text of a html button
                    view.evaluateJavascript(
                            "(function() { return (document.getElementsByClassName('nav_tab')[1].textContent); })();",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    // Process the HTML string here
                                    if (html != null) {
                                        Log.d("sk", html);
                                    }
                                }
                            });
                    // below code is to set text of a html button
//                    view.evaluateJavascript("document.getElementsByClassName('nav_tab')[1].innerText='Hello world'",null);
                }

           });
       }
    }
}
