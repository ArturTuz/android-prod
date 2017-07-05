## About
This is the Android sample app for Spot.IM `Conversation` with the native Android Spot.IM SDK. 
Spot.IM SDK allows easy integrating [Spot.IM](http://www.spot.im) into a native Android application. 

# Add Spot.IM SDK via Gradle

Add the SDK to Your build.gradle

```groovy
buildscript {
  repositories {
    maven { url 'http://spot.im.artifacts.s3.amazonaws.com/android' }
  }
}


allprojects {
    repositories {
        jcenter()
        maven { url 'http://spot.im.artifacts.s3.amazonaws.com/android' }
    }
}

dependencies {
...

    compile('spot.im:web-sdk:1.3@aar') {
        transitive = true;
    }
}
```


## Spot.IM Basic Conversation 

**In the application class add:**
``` java
SpotConversation.initConversation(this);
SpotConversation.getInstance().preload("SPOT_ID", "POST_ID");

```

**Loading the conversation:**

``` java
    SpotConversation.getInstance().setOnReadyListener(new SpotConversation.OnReadyListener() {
        @Override
        public void onConversationReady() {
            presentConversation();
        }
    });

    private void presentConversation() {
        ConversationFragment fragment = new ConversationFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(im.spot.sdk.R.anim.enter_from_right, im.spot.sdk.R.anim.exit_to_left, im.spot.sdk.R.anim.enter_from_left, im.spot.sdk.R.anim.exit_to_right);
        fragmentTransaction.add(R.id.conversationHolder, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
```

## Spot.IM IFrame Conversation

``` java
SpotConversationIFrameHandler handler = new SpotConversationIFrameHandler();

// Pass your WebViewClient 
handler.setSpotIFrameWebviewClient(client);

// Pass your WebView 
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
```

## Spot.IM SSO

**Start SSO**
``` java
SpotConversation.getInstance().startSSO(new SSOHandler() {
    @Override
    public void onFetchedCodeA(String codeA, SSOError error) {
        if (codeA == null && error == null) {
            // Already Logged in.
        }
        if (codeA != null) {
            // Fetch code B and pass it

            SpotConversation.getInstance().completeSSO("CODE_B", new OnSSOComplete() {
                @Override
                public void onSSOStateChanged(SSOError error) {
                    if (error == null) {
                        // Logged in
                    }
                }
            });
        }
    }
});
```

**Logout**
``` java
SpotConversation.getInstance().logout(new OnSSOComplete() {
                        @Override
                        public void onSSOStateChanged(SSOError error) {
                            if (error == null) {
                                // Logout successful
                            }
                        }
                    });
``` 
