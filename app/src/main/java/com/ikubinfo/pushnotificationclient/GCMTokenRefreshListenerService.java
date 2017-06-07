package com.ikubinfo.pushnotificationclient;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by egorari on 6/7/2017.
 */

public class GCMTokenRefreshListenerService extends InstanceIDListenerService {
    /**
     * When token refresh start service to get new token
     */
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
