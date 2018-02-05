package spotim.com.spotimdemoapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.support.v7.widget.SwitchCompat;
import android.widget.RadioGroup;

import im.spot.sdk.ConversationFragment;
import im.spot.sdk.SpotConfiguration;
import im.spot.sdk.SpotConversation;

public class MainActivity extends AppCompatActivity {

    private EditText mSpotID;
    private EditText mPostID;
    //    private ToggleButton mToggleButton;
//    private SwitchCompat mSwitch;
    private RadioGroup mRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView.setWebContentsDebuggingEnabled(true);
        mSpotID = findViewById(R.id.spot_id);
        mPostID = findViewById(R.id.post_id);
        // Pre-filled Texts, change if needed:
        // Prod:
        mSpotID.setText("sp_IjnMf2Jd");
        mPostID.setText("23329716");
        // Staging:
//        mSpotID.setText("sp_JRGmW7Ab");
//        mPostID.setText("42");

        // To Load a conversation through a WebView (not an iFrame):
//        mSwitch = findViewById(R.id.staging_switch);
//        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (buttonView.getId() == R.id.staging_switch) {
//                    if (buttonView.isChecked()) {
//                        buttonView.setText("Production");
//                    } else {
//                        buttonView.setText("Staging");
//                    }
//                }
//            }
//        });
        mRadio = findViewById(R.id.radioStaging);

        Button button = findViewById(R.id.load_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpotConfiguration configuration = new SpotConfiguration();
                configuration.setSpotId(mSpotID.getText().toString());
                configuration.setPostId(mPostID.getText().toString());
//                configuration.setStaging(!mSwitch.isChecked());
                configuration.setStaging(mRadio.getCheckedRadioButtonId() == R.id.staging);
                configuration.setSingleton(true);
                ConversationFragment spotIMFragment = ConversationFragment.newInstance(configuration);
                presentFragment(spotIMFragment);
            }
        });
        // for the Conversation Dismissal (Should also Clear Cache):
        Button terminateButton = findViewById(R.id.dismiss_spot_im);
        terminateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpotConversation.getInstance() != null) {
                    SpotConversation.getInstance().terminate();
                }
            }
        });
        Button iFrameButton = findViewById(R.id.iframe_btn);
        iFrameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iFrameIntent = new Intent(MainActivity.this, IFrameActivity.class);
                iFrameIntent.putExtra("spotID", mSpotID.getText().toString());
                iFrameIntent.putExtra("postID", mPostID.getText().toString());
                iFrameIntent.putExtra("isStaging", mRadio.getCheckedRadioButtonId() == R.id.staging);
                startActivity(iFrameIntent);
            }
        });
    }

    public void presentFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_holder, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

}
