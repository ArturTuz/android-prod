![android sdk 2x](https://user-images.githubusercontent.com/607917/35045982-4f117920-fb9e-11e7-81bd-f193a764d02b.jpg)

## Welcome to the Spot.IM SDK

Spot.IM SDK provides an easy integration with [Spot.IM](http://www.spot.im) into a native Android app. 

Here's a sample app that shows how to use the Spot.IM SDK for Android. 

## Conversation Preview

![screenshots - android](https://user-images.githubusercontent.com/607917/35328550-6d5063b2-0105-11e8-882f-d6a3d2bdf0f6.png)

## Getting started

To use the SDK you will need an active Spot.IM account. If you don't have it, get one [here](http://www.spot.im).  
You will need to know your Spot ID (which looks like 'sp_xxxxxxx'). 
If you don't know your Spot ID, login to the [admin dashboard](https://admin.spot.im) and have a look at the URL.

## Add Spot.IM SDK via Gradle

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

    compile('spot.im:web-sdk:1.5.0@aar') {
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

### Set Messages Count

If you want to present specific amount of messages you can use the overload method:
``` java
    SpotConversation.getInstance().preload(SPOT_ID, POST_ID, 8);
```

## Spot.IM IFrame Conversation

``` java
SpotConversationIFrameHandler handler = new SpotConversationIFrameHandler(SPOT_ID, POST_ID);

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

    @Override
    public void onIFrameHeightReady(int height) {
        Log.d("onIFrameHeightReady", "" + height);
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


## License

the Spot.IM SDK is released under a Custom license based on MIT. [See LICENSE](https://github.com/SpotIM/iOS-prod/blob/master/LICENSE) for details.

## Support

Feedback and inquires can be sent to love@spot.im
