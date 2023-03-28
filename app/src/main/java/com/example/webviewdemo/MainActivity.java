package com.example.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.android.louievoice.LouieSdk;
import com.android.louievoice.Utils.ContextFinder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    WebView webView;
    ImageView louieBtn;

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        getSupportActionBar().hide();
        louieBtn = findViewById(R.id.button_start_stop_voice_session);
        LouieSdk.INSTANCE.initializeSDK(this, Locale.getDefault().getLanguage());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.javatpoint.com/contact-us");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Browser_Home());
        louieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    permissionCheck();
                } else {
                    if (LouieSdk.INSTANCE.isVoiceSessionActive()) {
                        LouieSdk.INSTANCE.flushSpeechGame();
                    } else {
                        LouieSdk.INSTANCE.setCurrentScreen("MainActivity");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ContextFinder.INSTANCE.checkPermissionAndStartPrimaryCommand(MainActivity.this);
                        }
                    }
                }
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
            // below code is to click on a html element
//                    view.loadUrl("javascript:(function() { " +
//                            "var element = document.getElementsByClassName('nav_tab')[1]; " +
//                            "if (element) { " +
//                            "element.click(); " +
//                            "} " +
//                            "})()");
//            view.loadUrl("javascript:(function() { " +
//                    "var element = document.getElementById('discuss-tab').firstChild; " +
//                    "if (element) { " +
//                    "element.click(); " +
//                    "} " +
//                    "})()");
            // below code is to find text of a html button
//            view.evaluateJavascript(
//                    "(function() { return (document.getElementsByClassName('nav_tab')[1].textContent); })();",
//                    new ValueCallback<String>() {
//                        @Override
//                        public void onReceiveValue(String html) {
//                            // Process the HTML string here
//                            if (html != null) {
//                                Log.d("sk", html);
//                            }
//                        }
//                    });
            // below code is to set text of a html button
//                    view.evaluateJavascript("document.getElementsByClassName('nav_tab')[1].innerText='Hello world'",null);
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LouieSdk.INSTANCE.setCurrentScreen("MainActivity");//Setting current activity
        LouieSdk.INSTANCE.setCurrentActivity(MainActivity.this,null);


    }
    private void permissionCheck() {
        TedPermission.create()
                .setPermissions(Manifest.permission.RECORD_AUDIO)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        if(!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, "android.permission.RECORD_AUDIO")){
                            //permissionDeniedCountRetry=3;
                        }
//                        permissionDeniedConfirmation();
                    }
                }).check();
    }
}
