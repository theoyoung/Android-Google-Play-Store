package com.syenbhark.thetutorialforandroidbeginners;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Teaches how to unlock the screen by showing the simulation video and allow you to try it out.
 * cite: http://stackoverflow.com/questions/19745890/how-do-i-lock-phone-programmatically-android
 *
 * @author Syen
 */

public class F01HowToUnlockTheScreenActivity extends AppCompatActivity {

    private static final int ADMIN_INTENT = 15;
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;
    private boolean didUnlockTheScreen;
    private boolean isResumedAfterTryingItOut;

    /**
     * Initializes a device manager, a component to lock screen and a flag boolean variable.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f01_how_to_unlock_the_screen);

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, F01MyAdminReceiver.class);
        didUnlockTheScreen = false;
        isResumedAfterTryingItOut = false;
    }

    /**
     * Shows the simulation video when you click a button.
     *
     * @param view of an object(Button).
     */
    public void setOnClick(View view) {
        // Vibrates when you click the button.
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(R00ConstantTimeValues.VIBRATE_NORMAL);

        // starts the activity that plays the simulation video.
        startActivity(new Intent(F01HowToUnlockTheScreenActivity.this, F01UnlockTheScreenVideoActivity.class));
    }

    /**
     * Allows you to try 'unlock the basic screen' out by locking your device screen.
     *
     * @param view of an object(Button).
     */
    public void setOnClickTryUnlockOut(View view) {
        // Vibrates when you click the button.
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(R00ConstantTimeValues.VIBRATE_NORMAL);

        //
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.description));
        startActivityForResult(intent, ADMIN_INTENT);

        // Checks whether you can administer the device, or not.
        boolean isAdmin = mDevicePolicyManager.isAdminActive(mComponentName);

        // If you are the administer, locks the device screen and you are going to unlock the screen.
        if (isAdmin) {
            didUnlockTheScreen = true;
            mDevicePolicyManager.lockNow();
        } else {    // Otherwise, alert that you are not the administer.
            Toast.makeText(getApplicationContext(), getString(R.string.F01HowToUnlockTheScreenActivity_toast_alertAdmin), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Destroys the activity when it is resumed after doing unlock the screen.
     */
    protected void onResume() {
        super.onResume();

        // You are done this lesson!
        if (isResumedAfterTryingItOut) {
            finish();
            Toast.makeText(getApplicationContext(), getString(R.string.F01HowToUnlockTheScreenActivity_toast_complete), Toast.LENGTH_SHORT).show();
        }

        // After clicking the trying it out button, this activity will be resumed because you did unlock the screen.
        if(didUnlockTheScreen){
            isResumedAfterTryingItOut = true;
        }
    }
}
