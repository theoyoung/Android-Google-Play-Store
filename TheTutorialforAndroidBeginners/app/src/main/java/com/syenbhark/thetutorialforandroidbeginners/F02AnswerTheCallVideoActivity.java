package com.syenbhark.thetutorialforandroidbeginners;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.VideoView;

import java.util.concurrent.CyclicBarrier;

/**
 * Shows the simulation video which is one of common ways to answer the incoming call.
 *
 * @author Syen
 */
public class F02AnswerTheCallVideoActivity extends AppCompatActivity {
    final CyclicBarrier gate = new CyclicBarrier(3);
    Ringtone ringtone;
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
        // cite: http://pngimg.com/download/946 image
        VideoView video = (VideoView) findViewById(R.id.videoView_incoming_call);
        video.setVideoURI(Uri.parse(getString(R.string.uri_prefix) + R.raw.incoming_call));
        video.requestFocus();
        video.start();

        // Plays user's default ringtone when the phone rings in the simulation video.
        // cite: http://stackoverflow.com/questions/2618182/how-to-play-ringtone-alarm-sound-in-android
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));


        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {

                    while(true) {

                        sleep(3000);
                        ringtone.play();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread3 = new Thread() {
            @Override
            public void run() {
                try {

                    while(true) {

                        sleep(9000);
                        ringtone.stop();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };



        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {

                    while(true) {

                        sleep(R00ConstantTimeValues.INCOMING_CALL_VIDEO_TIME_OUT);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread1.start();
        thread3.start();
        thread2.start();
        /*
        // After watching it, finishes the activity.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // close this activity
                finish();
            }
        }, R00ConstantTimeValues.INCOMING_CALL_VIDEO_TIME_OUT);
        */
    }

}
