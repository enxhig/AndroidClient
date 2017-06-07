package com.ikubinfo.pushnotificationclient;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by egorari on 6/7/2017.
 */

public class GCMRegistrationIntentService extends IntentService {
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    public GCMRegistrationIntentService(){
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerToGcm();
    }

    private String registerToGcm(){
        Intent registrationComplete = null;
        String token = null;
        try{
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.v("GCMIntentService", "token:" +token);
            //notify the UI that registration is completed successfully
            registrationComplete = new Intent(REGISTRATION_SUCCESS);
            registrationComplete.putExtra("token", token);
        }catch(Exception e){
            Log.v("GCMIntentService","Registration error");
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }
        //send broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        return token;
    }
}
