package com.example.project4;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class GettingDeviceTokenService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String Device_Token= FirebaseInstanceId.getInstance().getToken();
        Log.d("DEVICE TOKEN",Device_Token);
    }
}