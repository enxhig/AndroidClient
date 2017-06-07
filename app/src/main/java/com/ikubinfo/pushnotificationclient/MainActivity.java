package com.ikubinfo.pushnotificationclient;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by egorari on 6/7/2017.
 */

public class MainActivity extends Activity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //check type of intent filter
                if (intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    //registration success
                    String token = intent.getStringExtra("token");
                    Toast.makeText(getApplicationContext(), "GCM token:" + token, Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    //registration error
                    Toast.makeText(getApplicationContext(), "GCM registration error!!", Toast.LENGTH_LONG).show();
                } else {
//tobe define
                }
            }
        };
        //check status of Google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (ConnectionResult.SUCCESS != resultCode){
            //check type of error
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                Toast.makeText(getApplicationContext(), "Google Play Service is not installed/enabled in this device", Toast.LENGTH_LONG).show();
                //So notification
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            }else{
                Toast.makeText(getApplicationContext(),"This device does not support Google Play Service", Toast.LENGTH_LONG).show();
            }
        }else{
            //start service
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.v("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter((GCMRegistrationIntentService.REGISTRATION_ERROR)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }
}

