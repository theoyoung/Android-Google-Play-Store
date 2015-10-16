package com.syenbhark.thetutorialforandroidbeginners;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Shows the simulation video which is one of common ways to answer the incoming call.
 *
 * @author Syen
 */
public class F02AnswerTheCallVideoActivity extends AppCompatActivity {
    /**
     * Plays the simulation video with removing its action bar and title bar.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f02_answer_the_call_video);

        // Makes it full screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        // Gets unlock video and plays it.
        VideoView video = (VideoView) findViewById(R.id.videoView_incoming_call);
        video.setVideoURI(Uri.parse(getString(R.string.uri_prefix) + R.raw.incoming_call));
        video.requestFocus();
        video.start();

        // After watching it, finishes the activity.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // close this activity
                finish();
            }
        }, R00ConstantTimeValues.INCOMING_CALL_VIDEO_TIME_OUT);
    }
}
