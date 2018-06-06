package spotim.com.spotimdemoapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.content.Intent;

import im.spot.sdk.ConversationFragment;
import im.spot.sdk.ConversationIFrameListener;
import im.spot.sdk.SpotConversation;
import im.spot.sdk.SpotConversationIFrameHandler;

public class IFrameActivity extends AppCompatActivity {

    private WebView mWebView;
    private  String spotID;
    private String postID;
    private boolean isStaging;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iframe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setting the SpotID and PostID Dynamically:
        Intent myIntent = getIntent(); // gets the previously created intent
        spotID = myIntent.getStringExtra("spotID");
        postID = myIntent.getStringExtra("postID");
        isStaging = myIntent.getBooleanExtra("isStaging", false);
        // Setup the WebView:
        initWebView();
        // load the Required URL:
        loadUrl();
    }

    private void loadUrl() {
        String host = "www.spot.im";
        if (isStaging) {
            host = "stagingv2.spot.im";
        }
        String url = "https://" + host + "/embed/modules/mobile-sdk/conversation/index.html?spot_id=" + spotID + "&post_id=" + postID + "&spot_im_read_only=true&device=android&message_count=4"
                ;

        mWebView.loadUrl(url);

        // Alternative Resources:
//      mWebView.loadUrl("https://www.spot.im/embed/modules/mobile-sdk/conversation/index.html?spot_id=sp_IjnMf2Jd&spot_im_read_only=true&device=android&message_count=4&post_id=23307176");
//      mWebView.loadUrl("https://stagingv2.spot.im/embed/modules/mobile-sdk/conversation/index.html?spot_id=sp_JRGmW7Ab&post_id=42");
// for Debugging:
//      mWebView.loadUrl("http://192.168.1.140:9044/conversation/index.html?spot_id=sp_JRGmW7Ab&device=android&post_id=42&spot_im_read_only=true");

    }

    private void initWebView() {

        mWebView = findViewById(R.id.iframe_webview);
        WebViewClient client = new WebViewClient();
        mWebView.setWebViewClient(client);
        mWebView.setWebChromeClient(new WebChromeClient());

        // Settings Config:
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        // Setting up iFrame Handler:
 //       SpotConversationIFrameHandler iFrameHandler = new SpotConversationIFrameHandler(spotID, postID);
        SpotConversationIFrameHandler iFrameHandler = new SpotConversationIFrameHandler(spotID, null);
//        iFrameHandler.setStaging(isStaging);
        iFrameHandler.setSpotIFrameWebview(mWebView);
        iFrameHandler.setSpotIFrameWebviewClient(client);
        iFrameHandler.setListener(new ConversationIFrameListener() {
            @Override
            public void shouldLoadConversation(ConversationFragment fragment) {
                findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onIFrameHeightReady(int height) {

            }
        });

    }

}
