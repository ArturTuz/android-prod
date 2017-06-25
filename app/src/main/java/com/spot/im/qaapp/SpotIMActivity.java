package com.spot.im.qaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import im.spot.sdk.ConversationIFrameListener;
import im.spot.sdk.SpotConversationFragment;
import im.spot.sdk.SpotConversationIFrameHandler;

public class SpotIMActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_im);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

        };
        webView.setWebViewClient(client);
        SpotConversationIFrameHandler handler = new SpotConversationIFrameHandler();
        handler.setSpotIFrameWebviewClient(client);
        handler.setSpotIFrameWebview(webView);
        handler.setListener(new ConversationIFrameListener() {
            @Override
            public void shouldLoadConversation(SpotConversationFragment fragment) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.spotIM_Holder, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        String url = "http://ec2-54-245-13-126.us-west-2.compute.amazonaws.com/SpotIMTest.html";
        Bundle bundle = getIntent().getBundleExtra("spotParams");
        if (bundle != null && bundle.getString("customURL") != null && bundle.getString("customURL").length() > 0) {
            url = bundle.getString("customURL");
        }

        webView.loadUrl(url);
    }
}
