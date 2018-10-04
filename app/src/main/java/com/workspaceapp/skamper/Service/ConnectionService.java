package com.workspaceapp.skamper.Service;

import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.calling.CallingActivity;

import static com.workspaceapp.skamper.SkamperApplication.sinchClient;

public class ConnectionService extends android.app.Service {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username = user.getEmail();
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(user.getEmail())
                .applicationKey("ae90c73b-4fcd-4a40-8a5b-ab8a0adb642a")
                .applicationSecret("0li5kxQXtUyF+D9ruk7MHg==")
                .environmentHost("sandbox.sinch.com")
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();
        SkamperApplication.sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {

            SkamperApplication.call = incomingCall;
            Intent intent = new Intent(getApplicationContext(),CallingActivity.class);
            startActivity(intent);


        }
    }

}
