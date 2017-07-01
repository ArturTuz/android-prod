package com.spot.im.qaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import im.spot.sdk.ConversationFragment;
import im.spot.sdk.ConversationIFrameListener;
import im.spot.sdk.SpotConversationFragment;
import im.spot.sdk.SpotConversationIFrameHandler;
import im.spot.sdk.SpotImWeb;

public class SpotIMActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WebView.setWebContentsDebuggingEnabled(true);
        setContentView(R.layout.activity_spot_im);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        WebViewClient client = new WebViewClient() {
            private boolean shouldOverrideUrl(WebView webView, Uri url) {
                if (url.getScheme().equals("conversational")) {
                    return true;
                }
                return false;
            }





            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return shouldOverrideUrl(view, Uri.parse(url));
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrl(view, request.getUrl());
            }

        };
        webView.setWebViewClient(client);
        SpotConversationIFrameHandler handler = new SpotConversationIFrameHandler();
        handler.setSpotIFrameWebviewClient(client);
        handler.setSpotIFrameWebview(webView);
        handler.setListener(new ConversationIFrameListener() {
            @Override
            public void shouldLoadConversation(ConversationFragment fragment) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.spotIM_Holder, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        String url = "http://ec2-54-245-13-126.us-west-2.compute.amazonaws.com/SpotIMTest.html";
//        String url = "http://10.0.0.8/conversational/SpotIMTest.html";
        Bundle bundle = getIntent().getBundleExtra("spotParams");
        if (bundle != null && bundle.getString("customURL") != null && bundle.getString("customURL").length() > 0) {
            url = bundle.getString("customURL");
        }

        webView.loadUrl(url);
    }

}
